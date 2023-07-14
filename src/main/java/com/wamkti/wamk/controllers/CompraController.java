package com.wamkti.wamk.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

import com.wamkti.wamk.dtos.CompraDTO;
import com.wamkti.wamk.dtos.CompraInputDTO;
import com.wamkti.wamk.dtos.CompraMinDTO;
import com.wamkti.wamk.entities.Compra;
import com.wamkti.wamk.entities.StatusCompra;
import com.wamkti.wamk.repositories.CompraRepository;
import com.wamkti.wamk.services.CompraService;

@RestController
@RequestMapping("/compras")
public class CompraController {
	
	@Autowired
	private CompraService compraService;
	
	@Autowired
	private CompraRepository compraRepository;
	
//	@GetMapping
//	public List<Compra> listar(){
//		List<Compra> list = compraService.findAll();
//		return list;
//	}

	@GetMapping
	public List<CompraMinDTO> listarMinDTO(){
		List<CompraMinDTO> list = compraService.findAllMinDTO();
		if(!list.isEmpty()) {
			for(CompraMinDTO compra : list) {
				Long id = compra.getId();
				compra.add(linkTo(methodOn(CompraController.class).buscar(id)).withSelfRel());
			}
		}
		
		return list;
	}
	
	@GetMapping("/page")
	public Page<CompraDTO> page(Pageable pageable){
		Page<Compra> pages = compraRepository.findAll(pageable);
		Page<CompraDTO> pagesDTO = pages.map(CompraDTO::new);
		return pagesDTO;
	}
	
	@GetMapping(value = "/{compraId}")
	public CompraDTO buscar(@PathVariable Long compraId) {
		CompraDTO compraDTO = compraService.findByIdDTO(compraId);
		compraDTO.add(linkTo(methodOn(CompraController.class).listarMinDTO()).withSelfRel());
		return compraDTO;
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Compra adcionarCompra(@RequestBody CompraInputDTO compraInputDTO) {
		Compra compra = new Compra();
		compra.setTotal(0.0);
		compra.setItems(0);
		compra.setStatus(StatusCompra.COMPRANDO);
		compraService.save(compra);
		return compra;
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
}
