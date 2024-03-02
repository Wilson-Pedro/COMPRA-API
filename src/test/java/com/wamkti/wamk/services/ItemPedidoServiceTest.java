package com.wamkti.wamk.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.wamkti.wamk.entities.Cliente;
import com.wamkti.wamk.entities.Produto;
import com.wamkti.wamk.repositories.ClienteRepository;
import com.wamkti.wamk.repositories.ItemPedidoRepository;
import com.wamkti.wamk.repositories.ProdutoRepository;

@SpringBootTest
class ItemPedidoServiceTest {
	
	@Autowired
	ItemPedidoService itemPedidoService;
	
	@Autowired
	ItemPedidoRepository itemPedidoRepository;
	
	@Autowired
	ProdutoRepository produtoRepository;
	
	@Autowired
	ClienteRepository clienteRepository;
	
	Cliente cliente = new Cliente();
	
	Produto produto = new Produto();

	@BeforeEach
	void setUp() {
		itemPedidoRepository.deleteAll();
		produtoRepository.deleteAll();
		clienteRepository.deleteAll();
		
		cliente = new Cliente(null, "Wilson Pedro", 5000.0);
		produto = new Produto(null, "Relógio", 20.0, cliente, 50);
	}

	@Test
	@DisplayName("Should Save The Order Item Successfully")
	void cretaeOrUpdateOrderItemCase01() {
		clienteRepository.save(cliente);
		produtoRepository.save(produto);
		
		assertEquals(0, itemPedidoRepository.count());
		
		itemPedidoService.criarOuAtulizarItemPedido(cliente, produto, 5);
		
		assertEquals(1, itemPedidoRepository.count());
	}
	
	@Test
	@Transactional
	@DisplayName("Should Update The Order Item Successfully")
	void cretaeOrUpdateOrderItemCase02() {
		clienteRepository.save(cliente);
		produtoRepository.save(produto);
		
		itemPedidoService.criarOuAtulizarItemPedido(cliente, produto, 5);
		itemPedidoService.criarOuAtulizarItemPedido(cliente, produto, 10);
		
		assertEquals(1, itemPedidoRepository.count());
	}

}
