package com.wmw.backend.integrador.model.cliente;

public enum TipoPessoa {
	FISICA("Física"),
	JURIDICA("Júridica");
	
	private String tipoPessoa;
	
	TipoPessoa(String tipoPessoa) {
		this.tipoPessoa = tipoPessoa;
	}
	
	public String getTipo() {
		return tipoPessoa;
	}
}
