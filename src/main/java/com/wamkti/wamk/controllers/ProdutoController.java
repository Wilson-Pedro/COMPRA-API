package com.wamkti.wamk.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import com.wamkti.wamk.dtos.ProdutoDTO;
import com.wamkti.wamk.entities.Produto;
import com.wamkti.wamk.services.ProdutoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {
	
	@Autowired
	private ProdutoService produtoService;

	@Autowired
	private ProdutoAssembler produtoAssembler;
	

	@GetMapping
	public List<ProdutoDTO> listar(){
		List<ProdutoDTO> list = produtoService.findAll();
		if(!list.isEmpty()) {
			for(ProdutoDTO produtoDTO : list) {
				Long id = produtoDTO.getId();
				produtoDTO.add(linkTo(methodOn(ProdutoController.class).buscar(id)).withSelfRel());
			}
		}
		return list;
	}
	
	@GetMapping(value = "/{produtoId}")
	public ProdutoDTO buscar(@PathVariable Long produtoId) {
		ProdutoDTO produtoDTO = produtoService.findByIdDTO(produtoId);
		produtoDTO.add(linkTo(methodOn(ProdutoController.class).listar()).withSelfRel());
		return produtoDTO;
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ProdutoDTO adcionarProduto(@Valid @RequestBody ProdutoDTO produtoDTO) {
		Produto obj = produtoAssembler.toEntity(produtoDTO);
		produtoService.save(obj);
		return produtoAssembler.toDTO(obj);
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
