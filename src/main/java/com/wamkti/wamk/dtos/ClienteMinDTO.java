package com.wamkti.wamk.dtos;

import java.io.Serializable;

public class ClienteMinDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String nome;
	private Double dinheiro;
	
	public ClienteMinDTO() {
	}

	public ClienteMinDTO(String nome, Double dinheiro) {
		this.nome = nome;
		this.dinheiro = dinheiro;
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
