package com.wamkti.wamk.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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

import com.wamkti.wamk.assembler.ProdutoAssembler;
import com.wamkti.wamk.dtos.ProdutoCompradoDTO;
import com.wamkti.wamk.dtos.ProdutoDTO;
import com.wamkti.wamk.dtos.inputs.ProdutoInputDTO;
import com.wamkti.wamk.entities.Produto;
import com.wamkti.wamk.repositories.ProdutoRepository;
import com.wamkti.wamk.services.ProdutoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private ProdutoAssembler produtoAssembler;
	

	@GetMapping
	public List<ProdutoDTO> listarDTO(){
		List<ProdutoDTO> list = produtoService.findAllDTO();
		if(!list.isEmpty()) {
			for(ProdutoDTO produtoDTO : list) {
				Long id = produtoDTO.getId();
				produtoDTO.add(linkTo(methodOn(ProdutoController.class).buscar(id)).withSelfRel());
			}
		}
		return list;
	}
	
	@GetMapping("/page")
	public Page<ProdutoDTO> page(Pageable pageable){
		Page<Produto> pages = produtoRepository.findAll(pageable);
		Page<ProdutoDTO> pagesDTO = pages.map(ProdutoDTO::new);
		return pagesDTO;
	}
	
	@GetMapping(value = "/{produtoId}")
	public ProdutoDTO buscar(@PathVariable Long produtoId) {
		ProdutoDTO produtoDTO = produtoService.findByIdDTO(produtoId);
		produtoDTO.add(linkTo(methodOn(ProdutoController.class).listarDTO()).withSelfRel());
		return produtoDTO;
	}
	
	@GetMapping(value = "/{clienteId}/clientes")
	public List<ProdutoCompradoDTO> findByCliente(@PathVariable Long clienteId) {
		List<ProdutoCompradoDTO> list = produtoService.findByCliente(clienteId);
		if(!list.isEmpty()) {
			for(ProdutoCompradoDTO obj : list) {
				Long id = obj.getId();
				obj.add(linkTo(methodOn(ProdutoController.class).buscar(id)).withSelfRel());
			}
		}
		return list;
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Object> adcionarProduto(@Valid @RequestBody ProdutoInputDTO produtoInputDto) {
		Produto obj = produtoAssembler.toEntity(produtoInputDto);
		if(produtoService.existsByNomeProduto(obj.getNomeProduto())) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Produto " + obj.getNomeProduto() + " já existe.");
		}
		obj.setId(null);
		produtoService.save(obj);
		var inputDto = new ProdutoInputDTO(obj);
		return ResponseEntity.ok().body(inputDto);
	}
	
	@PutMapping(value = "/{produtoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizarProduto(@Valid @RequestBody ProdutoDTO produtoDTO, 
			@PathVariable Long produtoId) {
		produtoService.atualizar(produtoDTO, produtoId);
	}
	
	@DeleteMapping(value = "/{produtoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletarProduto(@PathVariable Long produtoId) {
		produtoService.deletePorId(produtoId);
	}
}
