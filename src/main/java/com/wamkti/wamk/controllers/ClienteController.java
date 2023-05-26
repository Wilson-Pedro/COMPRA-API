package com.wamkti.wamk.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wamkti.wamk.dtos.ClienteDTO;
import com.wamkti.wamk.dtos.ClienteMinDTO;
import com.wamkti.wamk.services.ClienteService;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
	
	@Autowired
	private ClienteService clienteService;

	@GetMapping
	public List<ClienteMinDTO> ola() {
		List<ClienteMinDTO> list = clienteService.findAll();
		return list;
	}
	
	@GetMapping(value = "/{clienteId}")
	public ClienteDTO buscarPorId(@PathVariable Long clienteId) {
		ClienteDTO clienteDTO = clienteService.findById(clienteId);
		return clienteDTO;
	}
}
