package com.wmw.backend.integrador.exception;

public class ErrorHandler {
	private String campo;
	
	private String message;
	
	public ErrorHandler(String campo, String message) {
		this.campo = campo;
		this.message = message;
	}

	public String getCampo() {
		return campo;
	}

	public void setCampo(String campo) {
		this.campo = campo;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
