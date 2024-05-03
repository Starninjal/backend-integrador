package com.wmw.backend.integrador.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wmw.backend.integrador.model.cliente.Cliente;
public interface ClienteRepository extends JpaRepository<Cliente, Long>{
	Cliente findByDocumento(String documento);
}
