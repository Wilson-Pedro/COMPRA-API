package com.wamkti.wamk.dtos.min;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wamkti.wamk.dtos.ProdutoDTO;
import com.wamkti.wamk.entities.Cliente;

import jakarta.persistence.Embeddable;

@Embeddable
public class ClienteMinDTO extends RepresentationModel<ClienteMinDTO> implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String nome;
	
	@JsonIgnore
	private List<ProdutoDTO> produtos = new ArrayList<>();
	
	public ClienteMinDTO() {
	}

	public ClienteMinDTO(Cliente cliente) {
		id = cliente.getId();
		nome = cliente.getNome();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<ProdutoDTO> getProdutos() {
		return produtos;
	}
}
