package com.wamkti.wamk.acoes;

import java.io.Serializable;

public class Compre implements Serializable{
	private static final long serialVersionUID = 1L;

	private Long produtoId;
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
