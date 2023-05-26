package com.wamkti.wamk.dtos;

import java.io.Serializable;

import com.wamkti.wamk.entities.Cliente;

public class ClienteMinDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String nome;
	
	public ClienteMinDTO() {
	}

	public ClienteMinDTO(Cliente cliente) {
		id = cliente.getId();
		nome = cliente.getNome();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
}
