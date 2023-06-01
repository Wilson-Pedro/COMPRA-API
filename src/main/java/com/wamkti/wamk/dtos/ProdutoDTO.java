package com.wamkti.wamk.dtos;

import java.io.Serializable;

import org.springframework.beans.BeanUtils;

import com.wamkti.wamk.entities.Cliente;
import com.wamkti.wamk.entities.Produto;


public class ProdutoDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String nomeProduto;
	private Integer quantidade;
	private Double preco;
	private Cliente cliente;
	
	public ProdutoDTO() {
	}

	public ProdutoDTO(Produto produto) {
		BeanUtils.copyProperties(produto, this);
	}

	public ProdutoDTO(Long id, String nomeProduto, Integer quantidade, Double preco, Cliente cliente) {
		this.id = id;
		this.nomeProduto = nomeProduto;
		this.quantidade = quantidade;
		this.preco = preco;
		this.cliente = cliente;
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

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
}
