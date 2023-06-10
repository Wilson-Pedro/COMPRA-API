package com.wamkti.wamk.dtos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.wamkti.wamk.entities.Cliente;

public class ClienteDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String nome;
	private Double dinheiro;
	
	public ClienteDTO() {
	}

	public ClienteDTO(Cliente cliente) {
		BeanUtils.copyProperties(cliente, this);
	}
	
	public ClienteDTO(Long id, String nome, Double dinheiro) {
		this.id = id;
		this.nome = nome;
		this.dinheiro = dinheiro;
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

	public Double getDinheiro() {
		return dinheiro;
	}

	public void setDinheiro(Double dinheiro) {
		this.dinheiro = dinheiro;
	}
}
