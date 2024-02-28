package com.wamkti.wamk.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wamkti.wamk.entities.Cliente;
import com.wamkti.wamk.exceptions.EntityNotFoundException;
import com.wamkti.wamk.repositories.ClienteRepository;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	public List<Cliente> findAll() {
		return clienteRepository.findAll();
	}
	
	public Cliente findById(Long clienteId) {
		return clienteRepository.findById(clienteId)
				.orElseThrow(() -> new EntityNotFoundException());
	}

	public void deletePorId(Long clienteId) {
		clienteRepository.deleteById(clienteId);
		
	}

	@Transactional
	public Cliente salvar(Cliente cliente) {
		return clienteRepository.save(cliente);
	}

	@Transactional
	public Cliente atualizar(Cliente cliente, Long clienteId) {
		cliente.setId(clienteId);
		return clienteRepository.save(cliente);
	}

	public void atualizarDinheiro(Cliente cliente, Double subtotal) {
		cliente.setDinheiro(cliente.getDinheiro() - subtotal);
		clienteRepository.save(cliente);
	}
}
