package com.wmw.backend.integrador.exception;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerException {
	@ResponseStatus(HttpStatus.CONFLICT)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public List<ErrorHandler> camposHandler(MethodArgumentNotValidException e) {
		List<FieldError> campoErros = e.getBindingResult().getFieldErrors();
		
		List<ErrorHandler> list = new ArrayList<>();
		
		campoErros.forEach(error -> {
			list.add(new ErrorHandler(error.getField(), "Campo não pode ser vazio"));
		});
		
		return list;
		
	}
	
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(IllegalArgumentException.class)
	public String notFoundCliente() {
		return "Cliente não identificado";
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(ClienteException.class)
	public String clienteHandlerException(ClienteException e) {
		return e.getMessage();
	}
	
	@ResponseStatus(HttpStatus.CONFLICT)
	@ExceptionHandler(DataIntegrityViolationException.class)
	public String handleDataIntegrityViolation(DataIntegrityViolationException e) {
		if(e.getMessage().contains("documento")) {
			return "Documento existente dentro do banco de dados. Tente novamente";
		}
		return e.getMessage();
	}
}
