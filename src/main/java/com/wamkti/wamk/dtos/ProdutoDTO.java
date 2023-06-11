package com.wamkti.wamk.dtos;

import java.io.Serializable;

import org.springframework.beans.BeanUtils;

import com.wamkti.wamk.entities.Produto;

public class ProdutoDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String nomeProduto;
	private Double preco;
	
	public ProdutoDTO() {
	}

	public ProdutoDTO(Produto produto) {
		BeanUtils.copyProperties(produto, this);
	}

	public ProdutoDTO(Long id, String nomeProduto, Double preco, Long idCliente) {
		this.id = id;
		this.nomeProduto = nomeProduto;
		this.preco = preco;
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
