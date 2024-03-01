package com.wamkti.wamk.exceptions;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.wamkti.wamk.dtos.CompreDTO;
import com.wamkti.wamk.entities.Cliente;
import com.wamkti.wamk.entities.Produto;
import com.wamkti.wamk.repositories.ClienteRepository;
import com.wamkti.wamk.repositories.ProdutoRepository;
import com.wamkti.wamk.services.ProdutoService;

@SpringBootTest
class ProdutoExceptionsTest {
	
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
	void ExistingProductExceptionTest() {
		produtoService.save(new Produto(null, "Relógio", 20.0, null, 10));
		
		assertThrows(ExistingProductException.class, 
				() -> produtoService.save(new Produto(null, "Relógio", 20.0, null, 10)));
	}
	
	@Test
	void InsufficientBalanceExceptionTest() {
		cliente.setDinheiro(10.0);
		clienteRepository.save(cliente);
		produtoRepository.save(produto);
		
		Long clientId = clienteRepository.findAll().get(0).getId();
		Long productId = produtoRepository.findAll().get(0).getId();
	
		CompreDTO compreDto = new CompreDTO(clientId, productId, 5);
		
		assertThrows(InsufficientBalanceException.class, 
				() -> produtoService.comprar(compreDto));
	}
	
	@Test
	void InsufficientStockExceptionTest() {
		clienteRepository.save(cliente);
		produtoRepository.save(produto);
		
		Long clientId = clienteRepository.findAll().get(0).getId();
		Long productId = produtoRepository.findAll().get(0).getId();
	
		CompreDTO compreDto = new CompreDTO(clientId, productId, 50);
		
		assertThrows(InsufficientStockException.class, 
				() -> produtoService.comprar(compreDto));
	}

}
