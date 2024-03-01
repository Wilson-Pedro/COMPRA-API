package com.wamkti.wamk.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.wamkti.wamk.dtos.CompreDTO;
import com.wamkti.wamk.entities.Cliente;
import com.wamkti.wamk.entities.Produto;
import com.wamkti.wamk.repositories.ClienteRepository;
import com.wamkti.wamk.repositories.ProdutoRepository;

@SpringBootTest
class ProdutoServiceTest {
	
	@Autowired
	ProdutoService produtoService;
	
	@Autowired
	ProdutoRepository produtoRepository;
	
	@Autowired
	ClienteRepository clienteRepository;
	
	Cliente cliente = new Cliente();
	
	Produto produto = new Produto();

	@BeforeEach
	void test() {
		produtoRepository.deleteAll();
		clienteRepository.deleteAll();
		
		cliente = new Cliente(null, "Wilson Pedro", 5000.0);
		produto = new Produto(null, "Relógio", 20.0, cliente, 10);
	}
	
	@Test
	@DisplayName("Should Save The Product Successfully")
	void saveCase01() {
		clienteRepository.save(cliente);
		
		assertEquals(0, produtoRepository.count());
		
		Produto productSaved = produtoService.save(produto);
		
		assertNotNull(productSaved.getId());
		assertEquals(1, produtoRepository.count());
	}
	
	@Test
	@DisplayName("Should Fetch a List Of Products Seccessfully")
	void findAllCase01() {
		clienteRepository.save(cliente);
		produtoRepository.save(produto);
		
		List<Produto> list = produtoService.findAll();
		
		assertEquals(list.size(), clienteRepository.count());
	}
	
	@Test
	@DisplayName("Should Find The Product From The Id Seccessfully")
	void findByIdCase01() {
		clienteRepository.save(cliente);
		produtoRepository.save(produto);
		
		Long id = produtoRepository.findAll().get(0).getId();
		
		Produto produto = produtoService.findById(id);
		
		assertEquals("Relógio", produto.getNomeProduto());
		assertEquals(20.0, produto.getPreco());
		assertEquals(10, produto.getEstoque());
	}
	
	@Test
	@DisplayName("Should Update The Product Successfully")
	void updateCase01() {
		clienteRepository.save(cliente);
		produtoRepository.save(produto);
		produto.setPreco(30.0);
		
		Long id = produtoRepository.findAll().get(0).getId();
		Produto productUpdated = produtoService.atualizar(produto, id);
		
		assertEquals(30.0, productUpdated.getPreco());
		assertEquals(1, clienteRepository.count());
	}
	
	@Test
	@DisplayName("Should Updtae The Stock Seccessfully")
	void updateStockCase01() {
		clienteRepository.save(cliente);
		produtoRepository.save(produto);
	
		Long id = produtoRepository.findAll().get(0).getId();
	
		Produto produto = produtoService.atualizarEstoque(5, id);
	
		assertEquals(5, produto.getEstoque());
	}

	@Test
	@Transactional
	@DisplayName("Should Buy The Product Successfullybu")
	void buyCase01() {
		clienteRepository.save(cliente);
		produtoRepository.save(produto);
	
		Long clientId = clienteRepository.findAll().get(0).getId();
		Long productId = produtoRepository.findAll().get(0).getId();
	
		CompreDTO compreDto = new CompreDTO(clientId, productId, 5);
	
		produtoService.comprar(compreDto);
	
		Produto produto = produtoService.findById(productId);
	
		assertEquals(5, produto.getEstoque());
	}
	
	@Test
	@DisplayName("Should Delete The Product Successfully")
	void deleteCase01() {
		produtoRepository.save(new Produto(null, "Relógio", 20.0, null, 10));
		
		assertEquals(1, produtoRepository.count());
		
		Long id = produtoRepository.findAll().get(0).getId();
		produtoService.deletePorId(id);
		
		assertEquals(0, produtoRepository.count());
	}
}
