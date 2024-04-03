package com.wmw.backend.integrador.utils;

public class UtilCliente {
	private static final int[] pesoDV1 = { 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2 };
	private static final int[] pesoDV2 = { 6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2 };
	
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

	
}
