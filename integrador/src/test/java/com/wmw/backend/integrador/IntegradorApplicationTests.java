package com.wmw.backend.integrador;



import java.net.URI;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.wmw.backend.integrador.controller.ClienteController;
import com.wmw.backend.integrador.dto.ClienteDTO;
import com.wmw.backend.integrador.model.cliente.Cliente;
import com.wmw.backend.integrador.model.cliente.TipoPessoa;
import com.wmw.backend.integrador.service.ClienteService;

@SpringBootTest(classes = IntegradorApplication.class)
@AutoConfigureMockMvc
class BoraApplicationTests {
	
	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	ClienteService clienteService;
	
	@Autowired
	ClienteController clienteController;
	
	private static final String uri = "/cliente";
	
	@Test
	public void testDeveLançarAExceçãoDeViolacaoDeIntegracaoDevidoAoClienteTerOMesmoCpf() throws Exception {
		ClienteDTO clienteDTO = ClienteDTO.fromCliente(new Cliente("Cliente 01", TipoPessoa.FISICA, "136.213.859-24", "(48) 996136359", "cliente01@gmail.com"));

		clienteService.cadastrarCliente(clienteDTO);
		
		String json = "{\n" +
	              "    \"nome\": \"Cliente 01\",\n" +
	              "    \"tipoPessoa\": \"FISICA\",\n" +
	              "    \"documento\": \"136.213.859-24\",\n" +
	              "    \"telefone\": \"(48) 996136359\"\n" +
	              "}";
		
		mockMvc
		.perform(MockMvcRequestBuilders
				.post(new URI(uri))
				.content(json)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().is(409));	
	}
	
	@Test
	public void testDeveLançarAExceçãoDeViolacaoDeIntegracaoDevidoAoClienteTerOMesmoCNPJ() throws Exception {
		ClienteDTO clienteDTO = ClienteDTO.fromCliente(new Cliente("Cliente 01", TipoPessoa.JURIDICA, "50.199.526/0001-10", "(48) 996136359", "cliente01@gmail.com"));

		clienteService.cadastrarCliente(clienteDTO);
		
		String json = "{\n" +
	              "    \"nome\": \"Cliente 01\",\n" +
	              "    \"tipoPessoa\": \"JURIDICA\",\n" +
	              "    \"documento\": \"50.199.526/0001-10\",\n" +
	              "    \"telefone\": \"(48) 996136359\"\n" +
	              "}";
		
		
		
		mockMvc
		.perform(MockMvcRequestBuilders
				.post(new URI(uri))
				.content(json)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().is(409));	
		
	}
	
	@Test
	public void testDeveLançarAExceçãoClienteExceptionDevidoAoCPFDoClienteSerInvalido() throws Exception {
		String json = "{\n" +
	              "    \"nome\": \"Cliente 01\",\n" +
	              "    \"tipoPessoa\": \"FISICA\",\n" +
	              "    \"documento\": \"136.213.855-24\",\n" +
	              "    \"telefone\": \"(48) 996136359\"\n" +
	              "}";
		
	
		
		mockMvc
		.perform(MockMvcRequestBuilders
				.post(new URI(uri))
				.content(json)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().is(400));	

	}
	
	@Test
	public void testDeveLançarAExceçãoClienteExceptionDevidoAoCNPJDoClienteSerInvalido() throws Exception {
		String json = "{\n" +
	              "    \"nome\": \"Cliente 01\",\n" +
	              "    \"tipoPessoa\": \"JURIDICA\",\n" +
	              "    \"documento\": \"50.199.526/0002-10\",\n" +
	              "    \"telefone\": \"(48) 996136359\"\n" +
	              "}";
	
		
		mockMvc
		.perform(MockMvcRequestBuilders
				.post(new URI(uri))
				.content(json)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().is(400));	

	}
	
	
	@Test
	public void testDeveCadastrarUmClienteDevidoAoCPFSerValido() throws Exception {
		String json = "{\n" +
	              "    \"nome\": \"Cliente 01\",\n" +
	              "    \"tipoPessoa\": \"FISICA\",\n" +
	              "    \"documento\": \"010.129.319-43\",\n" +
	              "    \"telefone\": \"(48) 996136359\"\n" +
	              "}";
		
		mockMvc
		.perform(MockMvcRequestBuilders
				.post(new URI(uri))
				.content(json)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().is(200));	
	}
	
	@Test
	public void testDeveCadastrarUmClienteDevidoAoCNPJSerValido() throws Exception {
		String json = "{\n" +
	              "    \"nome\": \"Cliente 01\",\n" +
	              "    \"tipoPessoa\": \"JURIDICA\",\n" +
	              "    \"documento\": \"00.000.000/0001-91\",\n" +
	              "    \"telefone\": \"(48) 996136359\"\n" +
	              "}";

		mockMvc
		.perform(MockMvcRequestBuilders
				.post(new URI(uri))
				.content(json)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().is(200));	
	}
	
	@Test
	public void testDeveListarTodosOsClientes() throws Exception {
		mockMvc
		.perform(MockMvcRequestBuilders
				.get(new URI(uri)))
		.andExpect(MockMvcResultMatchers.status().is(200));
	}
	
	@Test
	public void testDeveListarUmClientePorUmIdentificador() throws Exception {
		ClienteDTO clienteDTO = ClienteDTO.fromCliente(new Cliente("Cliente 01", TipoPessoa.FISICA, "788.472.170-83", "(48) 996136359", "cliente01@gmail.com"));
		clienteService.cadastrarCliente(clienteDTO);
		mockMvc
		.perform(MockMvcRequestBuilders
				.get(new URI(uri + "/3")))
		.andExpect(MockMvcResultMatchers.status().is(200));
	}
	
	@Test
	public void testInformarQueoClienteNaoFoiIdentificadoDevidoAoIdentificadorSerInformadoIncorretamente() throws Exception {
		ClienteDTO clienteDTO = ClienteDTO.fromCliente(new Cliente("Cliente 01", TipoPessoa.FISICA, "952.966.275-04", "(48) 996136359", "cliente01@gmail.com"));
		clienteService.cadastrarCliente(clienteDTO);
		mockMvc
		.perform(MockMvcRequestBuilders
				.get(new URI(uri + "/10")))
		.andExpect(MockMvcResultMatchers.status().is(404));
	}
	
	
	@Test
	public void testDeveRemoverUmClienteDeAcordoComOIdentificadorFornecido() throws Exception {
		mockMvc
		.perform(MockMvcRequestBuilders
				.delete(new URI(uri + "/1")))
				.andExpect(MockMvcResultMatchers.status().is(201));
	}
	
	@Test
	public void testDeveRetornar409InformandoTodosOsCamposObrigatoriosQueEstaoVazios() throws Exception {
		String json = "{}";
		MvcResult result = mockMvc
		.perform(MockMvcRequestBuilders
				.post(new URI(uri))
				.content(json)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().is(409))
				.andReturn();
		
		System.out.println("\nTeste Validar Campos Obrigatórios \n\n" + result.getResponse().getContentAsString().replace(",", ", \n \n"));
	}
	


	@Test
	public void testDeveRetornarUmClienteComApenasOsDadosDeContatoEditados() throws Exception {
		String json = "{\n" +
				  "	   \"id\": 3,\n" +
	              "    \"nome\": \"Cliente 04\",\n" +
	              "    \"tipoPessoa\": \"FISICA\",\n" +
	              "    \"documento\": \"010.129.319-43\",\n" +
	              "    \"telefone\": \"(48) 56988710\",\n" +
	              "    \"email\": \"email01@gmail.com\"\n" +
	              "}\n\n";
		
		MvcResult mvcResult = mockMvc
		.perform(MockMvcRequestBuilders
				.put(new URI(uri))
				.content(json)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().is(200))
				.andReturn();
		
		System.out.println("\n Teste Alterar cliente \n\n" + mvcResult.getResponse().getContentAsString().replace(",", ", \n"));
		
	}

	

}
