package com.wamkti.wamk.controllers;

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

import com.wamkti.wamk.assembler.CompraAssembler;
import com.wamkti.wamk.dtos.CompraDTO;
import com.wamkti.wamk.dtos.CompraMinDTO;
import com.wamkti.wamk.entities.Compra;
import com.wamkti.wamk.entities.StatusCompra;
import com.wamkti.wamk.services.CompraService;

@RestController
@RequestMapping("/compras")
public class CompraController {
	
	@Autowired
	private CompraService compraService;

	@Autowired
	private CompraAssembler compraAssembler;
	

	@GetMapping
	public List<CompraMinDTO> listar(){
		return compraAssembler.toCollectionMinDTO(compraService.findAll());
	}
	
	@GetMapping(value = "/{compraId}")
	public CompraDTO buscar(@PathVariable Long compraId) {
		Compra compra = compraService.findById(compraId);
		return new CompraDTO(compra);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CompraDTO adcionarCompra(@RequestBody CompraDTO compraDTO) {
		Compra obj = compraAssembler.toEntity(compraDTO);
		obj.setStatus(StatusCompra.PENDENTE);
		compraService.save(obj);
		return compraAssembler.toDTO(obj);
	}
	
	@PutMapping(value = "/{compraId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizarCompra(@RequestBody CompraDTO compraDTO, 
			@PathVariable Long compraId) {
		compraService.atualizar(compraDTO, compraId);
	}
	
	@DeleteMapping(value = "/{compraId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletarCompra(@PathVariable Long compraId) {
		compraService.deletePorId(compraId);
	}
}
