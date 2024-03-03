package com.wamkti.wamk.controllers;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wamkti.wamk.dtos.ClienteDTO;
import com.wamkti.wamk.dtos.CompreDTO;
import com.wamkti.wamk.entities.Cliente;
import com.wamkti.wamk.entities.Produto;
import com.wamkti.wamk.repositories.ClienteRepository;
import com.wamkti.wamk.repositories.ItemPedidoRepository;
import com.wamkti.wamk.repositories.ProdutoRepository;

@SpringBootTest
@AutoConfigureMockMvc
class ClienteControllerTest {
	
	@Autowired
	ClienteRepository clienteRepository;
	
	@Autowired
	ProdutoRepository produtoRepository;
	
	@Autowired
	ItemPedidoRepository itemPedidoRepository;
	
	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	ObjectMapper objectMapper;
	
	Cliente cliente = new Cliente();
	
	Produto produto = new Produto();
	
	@BeforeEach
	void setUp() {
		itemPedidoRepository.deleteAll();
		produtoRepository.deleteAll();
		clienteRepository.deleteAll();
		
		cliente = new Cliente(null, "Wilson", 1000.0);
		produto = new Produto(null, "Relógio", 20.0, cliente, 10);
	}
	
	@Test
	@DisplayName("Should Save The Client Successfully")
	void saveClientCase01() throws Exception{
		assertEquals(0, clienteRepository.count());
		
		String jsonRequest = objectMapper.writeValueAsString(cliente);
		
		mockMvc.perform(post("/clientes")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonRequest))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.nome", equalTo("Wilson")))
				.andExpect(jsonPath("$.dinheiro", equalTo(1000.0)));
		
		assertEquals(1, clienteRepository.count());
		
	}
	
	@Test
	@DisplayName("Should Fetch a List Of Clients Successfully")
	void findAllCase01() throws Exception{
		clienteRepository.save(cliente);
		
		mockMvc.perform(get("/clientes"))
				.andExpect(status().isOk());
	}
	
	@Test
	@DisplayName("Should Find The Client From The Id Successfully")
	void findByIdCase01() throws Exception{
		clienteRepository.save(cliente);
		
		Long id = clienteRepository.findAll().get(0).getId();
		
		mockMvc.perform(get("/clientes/{clienteId}", id))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.nome", equalTo("Wilson")))
				.andExpect(jsonPath("$.dinheiro", equalTo(1000.0)));
	}
	
	@Test
	@DisplayName("Should Update The Client Successfully")
	void updateClientCase01() throws Exception{
		clienteRepository.save(cliente);
		
		Long id = clienteRepository.findAll().get(0).getId();
		
		String jsonRequest = objectMapper.writeValueAsString(new ClienteDTO("Wilson", 2000.0));
		
		mockMvc.perform(put("/clientes/{clienteId}", id)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonRequest))
				.andExpect(status().isNoContent());
		
		Cliente clientFinded = clienteRepository.findById(id).get();
		
		assertEquals(2000.0, clientFinded.getDinheiro());
		
	}
	
	@Test
	@DisplayName("Should Delete The Client From The Id Successfully")
	void deleteCase01() throws Exception{
		clienteRepository.save(cliente);
		assertEquals(1, clienteRepository.count());
		
		Long id = clienteRepository.findAll().get(0).getId();
		
		mockMvc.perform(delete("/clientes/{clienteId}", id))
				.andExpect(status().isNoContent());
		
		assertEquals(0, clienteRepository.count());
	}

	@Test
	@DisplayName("Should Buy The Product Successfully")
	void buye01() throws Exception{
		clienteRepository.save(cliente);
		produtoRepository.save(produto);
		
		Long clienteId = clienteRepository.findAll().get(0).getId();
		Long productId = produtoRepository.findAll().get(0).getId();
		
		String jsonRequest = objectMapper.writeValueAsString(new CompreDTO(clienteId, productId, 5));
		
		mockMvc.perform(post("/clientes/compras")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonRequest))
				.andExpect(status().isOk());
		
		Cliente clientFinded = clienteRepository.findById(clienteId).get();
		Produto productFinded = produtoRepository.findById(productId).get();
		
		assertEquals(900.0, clientFinded.getDinheiro());
		assertEquals(5, productFinded.getEstoque());
		
	}
}
