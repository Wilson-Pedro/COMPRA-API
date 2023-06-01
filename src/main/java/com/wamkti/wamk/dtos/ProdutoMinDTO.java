package com.wamkti.wamk.dtos;

import com.wamkti.wamk.entities.Produto;

import jakarta.persistence.Embedded;

public class ProdutoMinDTO {

	private Long id;
	private String nomeProduto;
	private Double preco;
	
	@Embedded
	private ClienteMinDTO clienteMinDTO;
	
	public ProdutoMinDTO() {
	}

	public ProdutoMinDTO(Produto produto) {
		id = produto.getId();
		nomeProduto = produto.getNomeProduto();
		preco = produto.getPreco();
	}

	public ProdutoMinDTO(Long id, String nomeProduto, Double preco, ClienteMinDTO clienteMinDTO) {
		this.id = id;
		this.nomeProduto = nomeProduto;
		this.preco = preco;
		this.clienteMinDTO = clienteMinDTO;
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

	public ClienteMinDTO getClienteMinDTO() {
		return clienteMinDTO;
	}

	public void setClienteMinDTO(ClienteMinDTO cliente) {
		this.clienteMinDTO = cliente;
	}
}
