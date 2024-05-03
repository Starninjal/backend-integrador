package com.wmw.backend.integrador.dto;

import java.util.List;

public class SincronizacaoDataDTO {
	private List<ClienteDTO> clientesDTO;
	
	private List<ClienteDTO> clientesDeletadosDTO;

	public List<ClienteDTO> getClientesDTO() {
		return clientesDTO;
	}

	public void setClientesDTO(List<ClienteDTO> clientes) {
		this.clientesDTO = clientes;
	}

	public List<ClienteDTO> getClientesDeletadosDTO() {
		return clientesDeletadosDTO;
	}

	public void setClientesDeletadosDTO(List<ClienteDTO> clientesDeletadosDTO) {
		this.clientesDeletadosDTO = clientesDeletadosDTO;
	}
	
	
}
