package com.wamkti.wamk.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
import com.wamkti.wamk.dtos.ClienteDTO;
import com.wamkti.wamk.dtos.min.ClienteMinDTO;
import com.wamkti.wamk.entities.Cliente;
import com.wamkti.wamk.entities.StatusCompra;
import com.wamkti.wamk.repositories.ClienteRepository;
import com.wamkti.wamk.repositories.ProdutoRepository;
import com.wamkti.wamk.services.ClienteService;
import com.wamkti.wamk.services.CompraService;
import com.wamkti.wamk.services.ProdutoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private CompraService compraService;

	@GetMapping
	public List<ClienteMinDTO> listarDTO() {
		List<ClienteMinDTO> list = clienteService.findAllMinDTO();
		if(!list.isEmpty()) {
			for(ClienteMinDTO cliente : list) {
				Long id = cliente.getId();
				cliente.add(linkTo(methodOn(ClienteController.class).buscarPorId(id)).withSelfRel());
			}
		}
		return list;
	}
	
	@GetMapping("/page")
	public ResponseEntity<Page<ClienteDTO>> paginar(Pageable pageable) {
		Page<Cliente> pages = clienteRepository.findAll(pageable);
		Page<ClienteDTO> pagesDto = pages.map(ClienteDTO::new);
		return ResponseEntity.ok(pagesDto);
	}
	
	@GetMapping(value = "/{clienteId}")
	public ClienteDTO buscarPorId(@PathVariable Long clienteId) {
		ClienteDTO clienteDto = clienteService.findByIdDTO(clienteId);
		clienteDto.add(linkTo(methodOn(ClienteController.class).listarDTO()).withSelfRel());
		return clienteDto;
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ClienteDTO adcionarCliente(@RequestBody @Valid ClienteDTO clienteDTO) {
		Cliente cliente = clienteService.copiarESalvar(clienteDTO);
		return new ClienteDTO(cliente);
	}
	
	@PutMapping(value = "/{clienteId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizarCliente(@RequestBody @Valid ClienteDTO clienteDTO, 
			@PathVariable Long clienteId) {
		clienteService.atualizarComDTO(clienteDTO, clienteId);
	}
	
	@DeleteMapping(value = "/{clienteId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletarCliente(@PathVariable Long clienteId) {
		clienteService.deletePorId(clienteId);
	}
	
	@PutMapping("/{clienteId}/compras")
	public ResponseEntity<Object> clienteCompraProduto(@PathVariable Long clienteId, 
			@RequestBody @Valid Compre compre) {
		var cliente = clienteRepository.findById(clienteId);
		var produto = produtoRepository.findById(compre.getProdutoId());
		var compra = compraService.findById(clienteId);
		int items = compre.getQuantidade();
		double subtotal = produto.get().getPreco() * items;
		double dinheiroClinte = cliente.get().getDinheiro();
		if(dinheiroClinte == 0 || subtotal > dinheiroClinte) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Você não possui saldo suficiente para fazer esta compra");
		} else if (compra.getStatus().equals(StatusCompra.FINALIZADA)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Sua compra já foi finalizada!");
		} else if (compre.getQuantidade() >= produto.get().getEstoque()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
					"Estoque insuficiente."
					+ "\nQuantidade demandada: " + compre.getQuantidade()
					+ "\nQuantidade em estoque: " + produto.get().getEstoque());
		}
	
		compraService.atualziar(compra, items, subtotal);
		clienteService.atualizarDinheiro(cliente.get(), subtotal);
		produtoService.atualizarEstoque(compre.getQuantidade(), compre.getProdutoId());
		produtoService.atulizarClienteIdDoProduto(clienteId, compre.getProdutoId());
		
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
	
	@PutMapping("/{clienteId}/continuacao")
	public ResponseEntity<Object> continuarComprando(@PathVariable Long clienteId){
		var compra = compraService.findById(clienteId);
		compra.setStatus(StatusCompra.COMPRANDO);
		compraService.save(compra);
		return ResponseEntity.ok("Você pode continuar comprando!");
		
	}
}
