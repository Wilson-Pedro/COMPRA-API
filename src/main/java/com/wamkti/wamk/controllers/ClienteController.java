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

import com.wamkti.wamk.dtos.ClienteDTO;
import com.wamkti.wamk.dtos.ClienteMinDTO;
import com.wamkti.wamk.entities.Cliente;
import com.wamkti.wamk.services.ClienteService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
	
	@Autowired
	private ClienteService clienteService;

	@GetMapping
	public List<ClienteMinDTO> listar() {
		List<ClienteMinDTO> list = clienteService.findAll();
		if(!list.isEmpty()) {
			for(ClienteMinDTO cliente : list) {
				Long id = cliente.getId();
				cliente.add(linkTo(methodOn(ClienteController.class).buscarPorId(id)).withSelfRel());
			}
		}
		return list;
	}
	
	@GetMapping(value = "/{clienteId}")
	public ClienteDTO buscarPorId(@PathVariable Long clienteId) {
		ClienteDTO clienteDTO = clienteService.findByIdDTO(clienteId);
		clienteDTO.add(linkTo(methodOn(ClienteController.class).listar()).withSelfRel());
		return clienteDTO;
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
}
