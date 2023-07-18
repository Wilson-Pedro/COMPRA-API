package com.wamkti.wamk.dtos.inputs;

import java.io.Serializable;

import com.wamkti.wamk.entities.Produto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ProdutoInputDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Long id;

	@NotBlank(message = "nomeProduto é obrigatório")
	private String nomeProduto;
	
	@NotNull(message = "preco não pode ser nulo")
	private Double preco;
	
	@NotNull(message = "estoque não pode ser nulo")
	private Integer estoque;
	
	public ProdutoInputDTO() {
	}
	
	public ProdutoInputDTO(Produto produto) {
		id = produto.getId();
		nomeProduto = produto.getNomeProduto();
		preco = produto.getPreco();
		estoque = produto.getEstoque();
	}

	public Long getId() {
		return id;
	}

	public String getNomeProduto() {
		return nomeProduto;
	}

	public Double getPreco() {
		return preco;
	}

	public Integer getEstoque() {
		return estoque;
	}
}
