package com.wamkti.wamk.dtos;

import java.io.Serializable;

import org.springframework.hateoas.RepresentationModel;

import com.wamkti.wamk.entities.Compra;

public class CompraMinDTO extends RepresentationModel<CompraMinDTO> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String nomeCliente;
	private Integer items;
	private Double total;
	
	public CompraMinDTO() {
	}
	
	public CompraMinDTO(Compra compra) {
		id = compra.getId();
		nomeCliente = compra.getCliente().getNome();
		items = compra.getItems();
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

	public Integer getItems() {
		return items;
	}

	public void setItems(Integer items) {
		this.items = items;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}
}
