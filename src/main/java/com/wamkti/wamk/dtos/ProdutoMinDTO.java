package com.wamkti.wamk.dtos;

import com.wamkti.wamk.entities.Produto;

public class ProdutoMinDTO {

	private Long id;
	private String nomeProduto;
	private Double preco;
	
	public ProdutoMinDTO() {
	}

	public ProdutoMinDTO(Produto produto) {
		id = produto.getId();
		nomeProduto = produto.getNomeProduto();
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

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}
}
