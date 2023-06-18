package com.wamkti.wamk.dtos;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.Objects;

import org.springframework.beans.BeanUtils;
import org.springframework.hateoas.RepresentationModel;

import com.wamkti.wamk.entities.Compra;
import com.wamkti.wamk.entities.StatusCompra;

public class CompraDTO extends RepresentationModel<CompraDTO> implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Integer clienteId;
	private Integer items;
	private Double total;
	private StatusCompra status;
	private OffsetDateTime dataCompra;
	
	public CompraDTO() {
	}
	
	public CompraDTO(Compra compra) {
		BeanUtils.copyProperties(compra, this);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getClienteId() {
		return clienteId;
	}

	public void setClienteId(Integer clienteId) {
		this.clienteId = clienteId;
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

	public StatusCompra getStatus() {
		return status;
	}

	public void setStatus(StatusCompra status) {
		this.status = status;
	}

	public OffsetDateTime getDataCompra() {
		return dataCompra;
	}

	public void setDataCompra(OffsetDateTime dataCompra) {
		this.dataCompra = dataCompra;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CompraDTO other = (CompraDTO) obj;
		return Objects.equals(id, other.id);
	}
}
