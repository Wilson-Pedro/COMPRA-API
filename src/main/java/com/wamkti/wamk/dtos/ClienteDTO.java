package com.wamkti.wamk.dtos;

import java.io.Serializable;

import org.springframework.beans.BeanUtils;
import org.springframework.hateoas.RepresentationModel;

import com.wamkti.wamk.entities.Cliente;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ClienteDTO extends RepresentationModel<ClienteDTO> implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	@NotBlank
	private String nome;
	
	@NotNull
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
