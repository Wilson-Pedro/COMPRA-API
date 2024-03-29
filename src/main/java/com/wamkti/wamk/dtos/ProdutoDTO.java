package com.wamkti.wamk.dtos;

import java.io.Serializable;

import org.springframework.beans.BeanUtils;
import org.springframework.hateoas.RepresentationModel;

import com.wamkti.wamk.entities.Produto;
import com.wamkti.wamk.projections.ProdutoMinProjection;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ProdutoDTO extends RepresentationModel<ProdutoDTO> implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	@NotBlank(message = "nomeProduto é obrigatório")
	private String nomeProduto;
	
	@NotNull(message = "preco não pode ser nulo")
	private Double preco;
	
	@NotNull(message = "estoque não pode ser nulo")
	private Integer estoque;
	
	public ProdutoDTO() {
	}

	public ProdutoDTO(Produto produto) {
		BeanUtils.copyProperties(produto, this);
	}

	public ProdutoDTO(Long id, String nomeProduto, Double preco, Integer estoque) {
		this.id = id;
		this.nomeProduto = nomeProduto;
		this.preco = preco;
		this.estoque = estoque;
	}
	
	public ProdutoDTO(ProdutoMinProjection entity) {
		id = entity.getId();
		nomeProduto = entity.getNomeProduto();
		preco = entity.getPreco();
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

//	public Integer getQuantidade() {
//		return quantidade;
//	}

	public Integer getEstoque() {
		return estoque;
	}

	public void setEstoque(Integer estoque) {
		this.estoque = estoque;
	}
}
