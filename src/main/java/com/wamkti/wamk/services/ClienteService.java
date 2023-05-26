package com.wamkti.wamk.services;

import org.springframework.beans.factory.annotation.Autowired;

import com.wamkti.wamk.repositories.ClienteRepository;

public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;
}
