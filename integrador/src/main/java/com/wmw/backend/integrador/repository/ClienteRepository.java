package com.wmw.backend.integrador.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wmw.backend.integrador.model.cliente.Cliente;
public interface ClienteRepository extends JpaRepository<Cliente, Long>{
	
}
