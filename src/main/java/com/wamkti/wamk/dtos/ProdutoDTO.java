package com.wamkti.wamk.dtos;

import java.io.Serializable;

import com.wamkti.wamk.entities.Produto;

public class ProdutoDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String nomeProduto;
	private Integer quantidade;
	private Double preco;
	
	public ProdutoDTO() {
	}

	public ProdutoDTO(Produto produto) {
		id = produto.getId();
		nomeProduto = produto.getNomeProduto();
		quantidade = produto.getQuantidade();
		preco = produto.getPreco();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNomeProduto() {
		return nomeProduto;
	}

	public void setNomeProduto(String nomeProduto) {
		this.nomeProduto = nomeProduto;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}
}
