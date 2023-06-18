package com.wamkti.wamk.acoes;

import java.io.Serializable;

import jakarta.validation.constraints.NotNull;

public class Compre implements Serializable{
	private static final long serialVersionUID = 1L;

	@NotNull
	private Long produtoId;
	
	@NotNull
	private Integer quantidade;
	
	public Compre() {
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
