package com.wmw.backend.integrador.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.wmw.backend.integrador.dto.ClienteDTO;
import com.wmw.backend.integrador.dto.SincronizacaoDataDTO;
import com.wmw.backend.integrador.exception.ClienteException;
import com.wmw.backend.integrador.model.cliente.Cliente;
import com.wmw.backend.integrador.model.cliente.TipoPessoa;
import com.wmw.backend.integrador.repository.ClienteRepository;
import com.wmw.backend.integrador.utils.UtilCliente;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	public Cliente cadastrarCliente(ClienteDTO clienteDTO) {
		validarCliente(clienteDTO);
		if(validarClienteExistente(clienteDTO)) {
		throw new DataIntegrityViolationException("Documento existente!");
	}
 		Cliente cliente = new Cliente();
		BeanUtils.copyProperties(clienteDTO, cliente);
		return clienteRepository.save(cliente);
	}

	public List<Cliente> listarClientes() {
		return clienteRepository.findAll();
	}

	public Cliente buscarClientePor(Long id) {
		return clienteRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Cliente não identificado"));
	}

	public Cliente alterarCliente(ClienteDTO clienteDTO) {
		Cliente clienteExistente = buscarClientePor(clienteDTO.id());
		if (!(clienteDTO.email() == null)) {
			clienteExistente.setEmail(clienteDTO.email());
			clienteExistente.setTelefone(clienteDTO.telefone());
			return clienteRepository.save(clienteExistente);
		}

		return clienteExistente;
	}

	public void excluirCliente(Long id) {
		Cliente cliente = listarClientes().stream()
				.filter(c -> c.getId() == id)
				.findFirst()
				.orElseThrow(() -> new NullPointerException("Cliente não encontrado"));
		UtilCliente.addClienteDeletado(cliente);
		clienteRepository.deleteById(id);
	}

	public void validarCliente(ClienteDTO clienteDTO) {
		UtilCliente.validarTelefone(clienteDTO);
		UtilCliente.validarEmail(clienteDTO);
		
		if (clienteDTO.tipoPessoa() == TipoPessoa.FISICA) {
			if (!validarCpf(clienteDTO.documento())) {
				throw new ClienteException("CPF inválido, tente novamente!");
			}
		} else if (clienteDTO.tipoPessoa() == TipoPessoa.JURIDICA) {
			if (!validarCnpj(clienteDTO.documento())) {
				throw new ClienteException("CNPJ inválido, tente novamente!");
			}
		}
	}


	public void sincronizarClientesMobile(SincronizacaoDataDTO sincronizacaoData) {
		List<ClienteDTO> clientesDTO = sincronizacaoData.getClientesDTO();
		List<ClienteDTO> clientesRemovidosDTO = sincronizacaoData.getClientesDeletadosDTO();
		if (clientesDTO != null) {
			for (ClienteDTO cliDTO : clientesDTO) {
				if (cliDTO.tipoPessoa() == TipoPessoa.FISICA || cliDTO.tipoPessoa() == TipoPessoa.JURIDICA){
					boolean clienteExiste = validarClienteExistente(cliDTO);

					if (!clienteExiste) {
						cadastrarCliente(cliDTO);
					} else {
						Cliente clienteOrigem = clienteRepository.findByDocumento(cliDTO.documento());
						cliDTO = cliDTO.withId(clienteOrigem.getId());
						alterarCliente(cliDTO);
					}
				} else {
					throw new IllegalArgumentException("Houve um erro na sincronização");
				}	
			}
			
			if (clientesRemovidosDTO.size() > 0) {
				removerClientesApi(clientesRemovidosDTO);
			}
		} else {
			excluirTodosClientes();
		}
	}
	private void excluirTodosClientes() {
		clienteRepository.deleteAll();
	}

	private void removerClientesApi(List<ClienteDTO> clientesRemovidos) {
		List<Cliente> clientesAtuais = listarClientes();
		for(ClienteDTO cliDTO: clientesRemovidos) {
			Cliente clienteOrigem = clienteRepository.findByDocumento(cliDTO.documento());
			cliDTO.withId(clienteOrigem.getId());
			if(validarClienteExistente(cliDTO)) {
				clienteRepository.deleteById(clienteOrigem.getId());
			}
		}
	}

	private boolean validarCpf(String cpf) {
		cpf = cpf.replaceAll("[^0-9]", "");

		if (cpf.length() != 11) {
			return false;
		}
		int digitoVerificador1 = UtilCliente.calcularDigitoCPF(cpf, 9, 10);
		int digitoVerificador2 = UtilCliente.calcularDigitoCPF(cpf, 10, 11);
		return Integer.parseInt(String.valueOf(cpf.charAt(9))) == digitoVerificador1
				&& Integer.parseInt(String.valueOf(cpf.charAt(10))) == digitoVerificador2;
	}

	private boolean validarCnpj(String cnpj) {

		if (cnpj == null || cnpj.length() != 18) {
			return false;
		}

		String regexRemoverLetras = "[^0-9]";

		cnpj = cnpj.replaceAll(regexRemoverLetras, "");

		int digitoVerificador1 = UtilCliente.calcularDigitoCNPJ(cnpj, 12);
		int digitoVerificador2 = UtilCliente.calcularDigitoCNPJ(cnpj, 13);

		return Integer.parseInt(String.valueOf(cnpj.charAt(12))) == digitoVerificador1
				&& Integer.parseInt(String.valueOf(cnpj.charAt(13))) == digitoVerificador2;

	}
	
	private boolean validarClienteExistente(ClienteDTO clienteDTO) {
		List<Cliente> clientes = listarClientes();
		return clientes.stream().filter(c -> c != null).anyMatch(c -> c.getDocumento().equals(clienteDTO.documento())); 
	}
}
