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
	public ResponseEntity<List<ProdutoDTO>> listarDTO(){
		List<ProdutoDTO> listDto = produtoService.findAll().stream()
				.map(x -> new ProdutoDTO(x)).toList();
		if(!listDto.isEmpty()) 
			for(ProdutoDTO produtoDTO : listDto) {
				Long id = produtoDTO.getId();
				produtoDTO.add(linkTo(methodOn(ProdutoController.class).buscar(id)).withSelfRel());
			}
		return ResponseEntity.ok(listDto);
	}
	
	@GetMapping("/page")
	public Page<ProdutoDTO> page(Pageable pageable){
		Page<Produto> pages = produtoRepository.findAll(pageable);
		return pages.map(ProdutoDTO::new);
	}
	
	@GetMapping(value = "/{produtoId}")
	public ResponseEntity<ProdutoDTO> buscar(@PathVariable Long produtoId) {
		Produto produto = produtoService.findById(produtoId);
		var produtoDTO = new ProdutoDTO(produto);
		produtoDTO.add(linkTo(methodOn(ProdutoController.class).listarDTO()).withSelfRel());
		return ResponseEntity.ok(produtoDTO);
	}
	
	@GetMapping(value = "/{clienteId}/clientes")
	public ResponseEntity<List<ProdutoCompradoDTO>> findByCliente(@PathVariable Long clienteId) {
		List<ProdutoCompradoDTO> list = produtoService.findByCliente(clienteId);
		if(!list.isEmpty()) {
			for(ProdutoCompradoDTO obj : list) {
				Long id = obj.getId();
				obj.add(linkTo(methodOn(ProdutoController.class).buscar(id)).withSelfRel());
			}
		}
		return ResponseEntity.ok(list);
	}
	
	@PostMapping
	public ResponseEntity<ProdutoInputDTO> adcionarProduto(@Valid @RequestBody ProdutoInputDTO produtoInputDto) {
		Produto obj = produtoService.save(produtoAssembler.toEntity(produtoInputDto));
		return ResponseEntity.status(HttpStatus.CREATED).body(new ProdutoInputDTO(obj));
	}
	
	@PutMapping(value = "/{produtoId}")
	public ResponseEntity<Void> atualizarProduto(@Valid @RequestBody ProdutoDTO produtoDTO, 
			@PathVariable Long produtoId) {
		produtoService.atualizar(produtoAssembler.toEntity(produtoDTO), produtoId);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping(value = "/{produtoId}")
	public ResponseEntity<Void> deletarProduto(@PathVariable Long produtoId) {
		produtoService.deletePorId(produtoId);
		return ResponseEntity.noContent().build();
	}
}
