package com.wmw.backend.integrador.dto;

import com.wmw.backend.integrador.model.cliente.Cliente;
import com.wmw.backend.integrador.model.cliente.TipoPessoa;

import jakarta.validation.constraints.NotNull;


public record ClienteDTO(Long id, @NotNull String nome, @NotNull TipoPessoa tipoPessoa, @NotNull String documento, @NotNull String telefone, String email) {
    public static ClienteDTO fromCliente(Cliente cliente) {
        return new ClienteDTO(
        	cliente.getId(),
            cliente.getNome(),
            cliente.getTipoPessoa(),
            cliente.getDocumento(),
            cliente.getTelefone(),
            cliente.getEmail()
        );	
    }
    
    public static Cliente fromClienteDTO(ClienteDTO clienteDTO) {
    	return new Cliente(
    			clienteDTO.id,
    			clienteDTO.nome,
    			clienteDTO.tipoPessoa,
    			clienteDTO.documento,
    			clienteDTO.telefone,
    			clienteDTO.email);
    }
    
    public ClienteDTO withId(Long id) {
        return new ClienteDTO(id, nome, tipoPessoa, documento, telefone, email);
    }
    
 
}
