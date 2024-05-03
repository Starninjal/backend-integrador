package com.wmw.backend.integrador.utils;

import java.util.ArrayList;
import java.util.List;

import com.wmw.backend.integrador.dto.ClienteDTO;
import com.wmw.backend.integrador.exception.ClienteException;
import com.wmw.backend.integrador.model.cliente.Cliente;

public class UtilCliente {
	private static final int[] pesoDV1 = { 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2 };
	private static final int[] pesoDV2 = { 6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2 };
	private static List<Cliente> clientesDeletados = new ArrayList<Cliente>();

	public static int calcularDigitoCPF(String cpf, int indice, int pesoInicial) {
		// TODO Auto-generated method stub
		int soma = 0;

		int peso = pesoInicial;

		for (int i = 0; i < indice; i++) {
			soma += Character.getNumericValue(cpf.charAt(i)) * peso--;
		}

		int mod = soma % 11;

		return mod < 2 ? 0 : 11 - mod;
	}

	public static int calcularDigitoCNPJ(String cnpj, int peso) {
		int[] pesos = pesoDV1;
		if (peso == 13) { // Se for o segundo dígito verificador
			pesos = pesoDV2;
		}

		int soma = 0;
		for (int i = 0; i < peso; i++) {
			int digito = Character.getNumericValue(cnpj.charAt(i));
			soma += digito * pesos[i];
		}
		int resto = soma % 11;
		return resto < 2 ? 0 : 11 - resto;

	}

	public static List<Cliente> getClientesDeletados() {
		return clientesDeletados;
	}

	public static void setClientesDeletados(List<Cliente> clientesDeletados) {
		UtilCliente.clientesDeletados = clientesDeletados;
	}

	public static void clearClientesDeletados() {
		UtilCliente.clientesDeletados.clear();
	}

	public static void addClienteDeletado(Cliente clienteDeletado) {
		UtilCliente.clientesDeletados.add(clienteDeletado);
	}
	
	public static void validarTelefone(ClienteDTO clienteDTO) {
		String telefone = clienteDTO.telefone();
		String regex = ".*[a-zA-Z].*";
		if (telefone.equals("") || telefone.matches(regex)) {
			throw new ClienteException("Telefone inválido!");
		}
	}
	
	public static void validarEmail(ClienteDTO clienteDTO) {
		String email = clienteDTO.email();
		if(email.length() > 0 && !email.contains("@")) {
			throw new ClienteException("Email não está formatado na forma correta!");
		}
	}

}
