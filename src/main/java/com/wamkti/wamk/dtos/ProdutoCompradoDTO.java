package com.wamkti.wamk.dtos;

import java.io.Serializable;

import org.springframework.beans.BeanUtils;
import org.springframework.hateoas.RepresentationModel;

import com.wamkti.wamk.entities.Produto;
import com.wamkti.wamk.projections.ProdutoMinProjection;

public class ProdutoCompradoDTO extends RepresentationModel<ProdutoCompradoDTO> implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Long clienteId;
	private String nomeProduto;
	private Double preco;
	private Integer quantidade;
	
	public ProdutoCompradoDTO() {
	}
	
	public ProdutoCompradoDTO(Produto produto) {
		BeanUtils.copyProperties(produto, this);
//		id = produto.getId();
//		nomeProduto = produto.getNomeProduto();
//		preco = produto.getPreco();
//		quantidade = produto.getQuantidade();
	}

	public ProdutoCompradoDTO(ProdutoMinProjection entity) {
		id = entity.getId();
		nomeProduto = entity.getNomeProduto();
		preco = entity.getPreco();
		quantidade = entity.getQuantidade();
	}
	
	public ProdutoCompradoDTO(Long id, String nomeProduto, Double preco, Integer quantidade) {
		this.id = id;
		this.nomeProduto = nomeProduto;
		this.preco = preco;
		this.quantidade = quantidade;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getClienteId() {
		return clienteId;
	}

	public void setClienteId(Long clienteId) {
		this.clienteId = clienteId;
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

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}
}
