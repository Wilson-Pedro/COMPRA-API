package com.wamkti.wamk.dtos;

import java.io.Serializable;

import jakarta.validation.constraints.NotNull;

public class CompreDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@NotNull(message = "clienteId não pode ser nulo")
	private Long clienteId;

	@NotNull(message = "produtoId não pode ser nulo")
	private Long produtoId;
	
	@NotNull(message = "quantidade produtoId não pode ser nulo")
	private Integer quantidade;
	
	public CompreDTO() {
	}

	public Long getClienteId() {
		return clienteId;
	}

	public Long getProdutoId() {
		return produtoId;
	}

	public void setProdutoId(Long produtoId) {
		this.produtoId = produtoId;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}
}
