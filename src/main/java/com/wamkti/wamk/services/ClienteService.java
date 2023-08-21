package com.wamkti.wamk.services;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wamkti.wamk.dtos.ClienteDTO;
import com.wamkti.wamk.dtos.min.ClienteMinDTO;
import com.wamkti.wamk.entities.Cliente;
import com.wamkti.wamk.repositories.ClienteRepository;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	public List<ClienteMinDTO> findAllMinDTO() {
		List<Cliente> list = clienteRepository.findAll();
		return list.stream().map(x -> new ClienteMinDTO(x)).toList();
	}
	
//	@SuppressWarnings("unchecked")
//	public Page<ClienteMinDTO> page(Pageable pageable) {
//		Page<Cliente> list = clienteRepository.findAll(pageable);
//		return (Page<ClienteMinDTO>) list.stream().map(x -> new ClienteMinDTO(x)).toList();
//	}
	
	public Cliente findById(Long clienteId) {
		Cliente cliente = clienteRepository.findById(clienteId).get();
		return cliente;
	}

	public ClienteDTO findByIdDTO(Long clienteId) {
		Cliente cliente = clienteRepository.findById(clienteId).get();
		return new ClienteDTO(cliente);
	}

	public void save(Cliente cliente) {
		clienteRepository.save(cliente);
	}

	public void deletePorId(Long clienteId) {
		clienteRepository.deleteById(clienteId);
		
	}

	@Transactional
	public Cliente copiarESalvar(ClienteDTO clienteDTO) {
		var cliente = new Cliente();
		BeanUtils.copyProperties(clienteDTO, cliente);
		clienteRepository.save(cliente);
		return cliente;
	}

	@Transactional
	public void atualizarComDTO(ClienteDTO clienteDTO, Long clienteId) {
		var cliente = new Cliente();
		BeanUtils.copyProperties(clienteDTO, cliente);
		cliente.setId(clienteId);
		clienteRepository.save(cliente);
	}

	public void atualizarDinheiro(Cliente cliente, double subtotal) {
		cliente.setDinheiro(cliente.getDinheiro() - subtotal);
		clienteRepository.save(cliente);
	}
}
