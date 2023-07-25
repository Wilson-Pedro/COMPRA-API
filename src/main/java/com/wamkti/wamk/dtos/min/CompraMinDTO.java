package com.wamkti.wamk.dtos.min;

import java.io.Serializable;

import org.springframework.hateoas.RepresentationModel;

import com.wamkti.wamk.entities.Compra;

public class CompraMinDTO extends RepresentationModel<CompraMinDTO> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String nomeCliente;
	private Integer quantidadeItems;
	private Double total;
	
	public CompraMinDTO() {
	}
	
	public CompraMinDTO(Compra compra) {
		id = compra.getId();
		nomeCliente = compra.getCliente().getNome();
		quantidadeItems = compra.getQuantidadeItems();
		total = compra.getTotal();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNomeCliente() {
		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}

	public Integer getQuantidadeItems() {
		return quantidadeItems;
	}

	public void setQuantidadeItems(Integer quantidadeItems) {
		this.quantidadeItems = quantidadeItems;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}
}
