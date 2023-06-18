package com.wamkti.wamk.exceptionhandler;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public class Problema implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String titulo;
	private Integer status;
	private OffsetDateTime dataHora;
	private List<Campo> campos = new ArrayList<>();
	
	public Problema() {
	}

	public Problema(String titulo, Integer status, OffsetDateTime dataHora, List<Campo> campos) {
		this.titulo = titulo;
		this.status = status;
		this.dataHora = dataHora;
		this.campos = campos;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public OffsetDateTime getDataHora() {
		return dataHora;
	}

	public void setDataHora(OffsetDateTime dataHora) {
		this.dataHora = dataHora;
	}

	public List<Campo> getCampos() {
		return campos;
	}

	public void setCampos(List<Campo> campos) {
		this.campos = campos;
	}
}
