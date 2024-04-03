package com.wmw.backend.integrador.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wmw.backend.integrador.dto.ClienteDTO;
import com.wmw.backend.integrador.model.cliente.Cliente;
import com.wmw.backend.integrador.service.ClienteService;

import jakarta.validation.Valid;
@RestController
@RequestMapping("/cliente")
public class ClienteController {
	
	@Autowired
	public ClienteService clienteService;
	
	@GetMapping
	public ResponseEntity<List<ClienteDTO>> listarCliente() {
		List<Cliente> clientes = clienteService.listarClientes();
		return ResponseEntity.ok(clientes.stream().map(ClienteDTO::fromCliente).toList());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ClienteDTO> listarClientePor(@PathVariable("id") Long id) {
		Cliente clienteBuscado = clienteService.buscarClientePor(id);
		return ResponseEntity.ok(ClienteDTO.fromCliente(clienteBuscado));
		
	}
	
	@PostMapping
	public ResponseEntity<ClienteDTO> cadastrarCliente(@RequestBody @Valid ClienteDTO clienteDTO) {
			Cliente cliente = clienteService.cadastrarCliente(clienteDTO);
			return ResponseEntity.ok(ClienteDTO.fromCliente(cliente));

	}
	
	@PutMapping
	public ResponseEntity<ClienteDTO> alterarCliente(@RequestBody  @Valid ClienteDTO clienteDTO) {
		Cliente cliente = clienteService.alterarCliente(clienteDTO);
		return ResponseEntity.ok(ClienteDTO.fromCliente(cliente));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity excluirCliente(@PathVariable("id") Long id) {
		if(id == 0) {
			return ResponseEntity.badRequest().body("Id não foi informado! Tente novamente!");
		}
		clienteService.excluirCliente(id);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

}

