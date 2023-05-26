package com.wamkti.wamk.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wamkti.wamk.dtos.ClienteDTO;
import com.wamkti.wamk.dtos.ClienteMinDTO;
import com.wamkti.wamk.entities.Cliente;
import com.wamkti.wamk.repositories.ClienteRepository;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	public List<ClienteMinDTO> findAll() {
		List<Cliente> list = clienteRepository.findAll();
		return list.stream().map(x -> new ClienteMinDTO(x)).toList();
	}

	public ClienteDTO findById(Long clienteId) {
		Cliente cliente = clienteRepository.findById(clienteId).get();
		return new ClienteDTO(cliente);
	}

	public void save(Cliente cliente) {
		clienteRepository.save(cliente);
	}
}
