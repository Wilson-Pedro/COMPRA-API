package com.wamkti.wamk.dtos;

import java.io.Serializable;

public class CompraInputDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Long clienteId;

	public Long getClienteId() {
		return clienteId;
	}

	public void setClienteId(Long clienteId) {
		this.clienteId = clienteId;
	}
}
