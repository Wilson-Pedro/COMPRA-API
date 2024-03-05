package com.wamkti.wamk.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.wamkti.wamk.dtos.CompreDTO;
import com.wamkti.wamk.entities.Cliente;
import com.wamkti.wamk.entities.Produto;
import com.wamkti.wamk.projections.ProdutoMinProjection;
import com.wamkti.wamk.services.ProdutoService;

@SpringBootTest
class ProdutoRepositoryTest {

	@Autowired
	ProdutoService produtoService;
	
	@Autowired
	ProdutoRepository produtoRepository;
	
	@Autowired
	ClienteRepository clienteRepository;
	
	@Autowired
	ItemPedidoRepository itemPedidoRepository;
	
	Cliente cliente = new Cliente();
	
	Produto produto = new Produto();

	@BeforeEach
	void setUp() {
		itemPedidoRepository.deleteAll();
		produtoRepository.deleteAll();
		clienteRepository.deleteAll();
		
		cliente = new Cliente(null, "Wilson Pedro", 5000.0);
		produto = new Produto(null, "Relógio", 20.0, cliente, 10);
	}
	
	@Test
	@DisplayName("Should Fetch a List Of Product From the ClientId Seccessfully")
	void findByClienteCase01() {
		clienteRepository.save(cliente);
		produtoRepository.save(produto);
		produtoRepository.save(new Produto(null, "Pencil", 2.0, null, 100));
	
		Long clienteId = clienteRepository.findAll().get(0).getId();
		Long productId = produtoRepository.findAll().get(0).getId();
	
		produtoService.comprar(new CompreDTO(clienteId, productId, 5));
		
		List<ProdutoMinProjection> list = produtoRepository.searchByList(clienteId);
	
		assertEquals(2, produtoRepository.count());
		assertEquals(1, list.size());
	}

}
