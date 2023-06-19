package com.wamkti.wamk.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.wamkti.wamk.acoes.Compre;
import com.wamkti.wamk.assembler.CompraAssembler;
import com.wamkti.wamk.dtos.CompraDTO;
import com.wamkti.wamk.dtos.CompraMinDTO;
import com.wamkti.wamk.entities.Compra;
import com.wamkti.wamk.entities.StatusCompra;
import com.wamkti.wamk.repositories.ClienteRepository;
import com.wamkti.wamk.repositories.ProdutoRepository;
import com.wamkti.wamk.services.ClienteService;
import com.wamkti.wamk.services.CompraService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/compras")
public class CompraController {
	
	@Autowired
	private CompraService compraService;

	@Autowired
	private CompraAssembler compraAssembler;
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	

	@GetMapping
	public List<CompraMinDTO> listar(){
		List<CompraMinDTO> list = compraService.findAll();
		if(!list.isEmpty()) {
			for(CompraMinDTO compra : list) {
				Long id = compra.getId();
				compra.add(linkTo(methodOn(CompraController.class).buscar(id)).withSelfRel());
			}
		}
		
		return list;
	}
	
	@GetMapping(value = "/{compraId}")
	public CompraDTO buscar(@PathVariable Long compraId) {
		CompraDTO compraDTO = compraService.findByIdDTO(compraId);
		compraDTO.add(linkTo(methodOn(CompraController.class).listar()).withSelfRel());
		return compraDTO;
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CompraDTO adcionarCompra(@RequestBody CompraDTO compraDTO) {
		Compra obj = compraAssembler.toEntity(compraDTO);
		obj.setStatus(StatusCompra.COMPRANDO);
		compraService.save(obj);
		return compraAssembler.toDTO(obj);
	}
	
	@PutMapping(value = "/{compraId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizarCompra(@RequestBody CompraDTO compraDTO, 
			@PathVariable Long compraId) {
		compraService.atualizarComDTO(compraDTO, compraId);
	}
	
	@DeleteMapping(value = "/{compraId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletarCompra(@PathVariable Long compraId) {
		compraService.deletePorId(compraId);
	}
	
	@PutMapping("/{clienteId}/compra")
	public ResponseEntity<Object> clienteCompraProduto(@PathVariable Long clienteId, 
			@Valid @RequestBody Compre compre) {
		var cliente = clienteRepository.findById(clienteId);
		var produto = produtoRepository.findById(compre.getProdutoId());
		var compra = compraService.findById(clienteId);
		int items = compre.getQuantidade();
		double subtotal = produto.get().getPreco() * items;
		double dinheiroClinte = cliente.get().getDinheiro();
		if(dinheiroClinte == 0 || subtotal > dinheiroClinte) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Você não possui saldo suficiente para fazer esta compra");
		} else if (!compra.getStatus().equals(StatusCompra.FINALIZADA)) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Sua compra já foi finalizada!");
		}
	
		compraService.atualziar(compra, items, subtotal);
		clienteService.atualizarDinheiro(cliente.get(), subtotal);
		
		return ResponseEntity.ok("Compra realizado com suceesso");
	}
	
	@PutMapping("/{clienteId}/finalizacao")
	public ResponseEntity<Object> finalizarCompra(@PathVariable Long clienteId){
		var compra = compraService.findById(clienteId);
		compra.setStatus(StatusCompra.FINALIZADA);
		compra.setDataCompra(OffsetDateTime.now());
		compraService.save(compra);
		return ResponseEntity.ok("Compra finalizada!");
		
	}
}
