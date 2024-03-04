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
import com.wamkti.wamk.dtos.ProdutoDTO;
import com.wamkti.wamk.dtos.inputs.ProdutoInputDTO;
import com.wamkti.wamk.entities.Cliente;
import com.wamkti.wamk.entities.Produto;
import com.wamkti.wamk.repositories.ClienteRepository;
import com.wamkti.wamk.repositories.ItemPedidoRepository;
import com.wamkti.wamk.repositories.ProdutoRepository;

@SpringBootTest
@AutoConfigureMockMvc
class ProdutoControllerTest {

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
	@DisplayName("Should Save The Product Successfully")
	void saveProductCase01() throws Exception {
		assertEquals(0, produtoRepository.count());
		
		ProdutoInputDTO produtoInputDTO = new ProdutoInputDTO(produto);

		String jsonRequest = objectMapper.writeValueAsString(produtoInputDTO);
		
		mockMvc.perform(post("/produtos")
			.contentType(MediaType.APPLICATION_JSON)
			.content(jsonRequest))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.nomeProduto", equalTo("Relógio")))
			.andExpect(jsonPath("$.preco", equalTo(20.0)))
			.andExpect(jsonPath("$.estoque", equalTo(10)));

		assertEquals(1, produtoRepository.count());
	}

	@Test
	@DisplayName("Should Fetch A List Of Product From Id Successfully")
	void findAllCase01() throws Exception {
		clienteRepository.save(cliente);
		produtoRepository.save(produto);
		
		mockMvc.perform(get("/produtos"))
			.andExpect(status().isOk());

		assertEquals(1, produtoRepository.count());
	}

	@Test
	@DisplayName("Should Page A List Of Products Successfully")
	void pageCase01() throws Exception {
		clienteRepository.save(cliente);
		produtoRepository.save(produto);
		
		mockMvc.perform(get("/produtos/page"))
			.andExpect(status().isOk());

		assertEquals(1, produtoRepository.count());
	}	

	@Test
	@DisplayName("Should Find The Product From The Id Successfully")
	void findByIdCase01() throws Exception {
		clienteRepository.save(cliente);
		produtoRepository.save(produto);

		Long id = produtoRepository.findAll().get(0).getId();
		
		mockMvc.perform(get("/produtos/{produtoId}", id))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.nomeProduto", equalTo("Relógio")))
			.andExpect(jsonPath("$.preco", equalTo(20.0)))
			.andExpect(jsonPath("$.estoque", equalTo(10)));

	}

	@Test
	@DisplayName("Should Update The Product Successfully")
	void updateProductCase01() throws Exception {
		clienteRepository.save(cliente);
		produtoRepository.save(produto);

		Long id = produtoRepository.findAll().get(0).getId();

		String jsonRequest = objectMapper.writeValueAsString(new ProdutoDTO(null, "Relógio", 30.0, 10));
		
		mockMvc.perform(put("/produtos/{produtoId}", id)
			.contentType(MediaType.APPLICATION_JSON)
			.content(jsonRequest))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.preco", equalTo(30.0)));

		assertEquals(1, produtoRepository.count());
	}

	@Test
	@DisplayName("Should Delete The Product Successfully")
	void deleteCase01() throws Exception {
		clienteRepository.save(cliente);
		produtoRepository.save(produto);
		
		assertEquals(1, produtoRepository.count());

		Long id = produtoRepository.findAll().get(0).getId();
		
		mockMvc.perform(delete("/produtos/{produtoId}", id))
			.andExpect(status().isNoContent());
		
		assertEquals(0, produtoRepository.count());
	}
}
