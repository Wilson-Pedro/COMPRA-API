package com.wamkti.wamk.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.wamkti.wamk.entities.Cliente;
import com.wamkti.wamk.repositories.ClienteRepository;
import com.wamkti.wamk.repositories.ItemPedidoRepository;
import com.wamkti.wamk.repositories.ProdutoRepository;

@SpringBootTest
class ClienteServiceTest {
	
	@Autowired
	ClienteService clienteService;
	
	@Autowired
	ClienteRepository clienteRepository;
	
	@Autowired
	ProdutoRepository produtoRepository;
	
	@Autowired
	ItemPedidoRepository itemPedidoRepository;
	
	Cliente cliente = new Cliente();
	
	@BeforeEach
	void setUp() {
		itemPedidoRepository.deleteAll();
		produtoRepository.deleteAll();
		clienteRepository.deleteAll();
		
		cliente = new Cliente(null, "Wilson", 1000.0);
	}

	@Test
	@DisplayName("Should Save The Client Successfully")
	void saveCase01() {
		assertEquals(0, clienteRepository.count());
		
		Cliente clientSaved = clienteService.salvar(cliente);
		
		assertNotNull(clientSaved.getId());
		assertEquals(1, clienteRepository.count());
	}
	
	@Test
	@DisplayName("Should Fetch a List Of Clients Seccessfully")
	void findAllCase01() {
		clienteService.salvar(cliente);
		
		List<Cliente> list = clienteService.findAll();
		
		assertEquals(list.size(), clienteRepository.count());
	}

	@Test
	@DisplayName("Should Find The Client From The Id Successfully")
	void findById01() {
		clienteService.salvar(cliente);
		
		Long id = clienteRepository.findAll().get(0).getId();
		
		Cliente clientFinded = clienteService.findById(id);
		
		assertEquals("Wilson", clientFinded.getNome());
		assertEquals(1000.0, clientFinded.getDinheiro());
	}
	
	@Test
	@DisplayName("Should Update The Client Successfully")
	void updateCase01() {
		clienteService.salvar(cliente);
		cliente.setDinheiro(2000.0);
		
		Long id = clienteRepository.findAll().get(0).getId();
		
		Cliente clientUpdated = clienteService.atualizar(cliente, id);
		
		assertEquals(2000.0, clientUpdated.getDinheiro());
		
		assertEquals(1, clienteRepository.count());
	}
	
	@Test
	@DisplayName("Should Update Money Successfully")
	void updateMoneyCase01() {
		clienteService.salvar(cliente);
		clienteService.atualizarDinheiro(cliente, 500.0);
		
		Long id = clienteRepository.findAll().get(0).getId();
		Cliente clientFinded = clienteService.findById(id);
		
		assertEquals(500.0, clientFinded.getDinheiro());
	}
	
	@Test
	@DisplayName("Should Delete The Client Successfully")
	void DeleteByIdCase01() {
		clienteService.salvar(cliente);
		assertEquals(1, clienteRepository.count());
		
		Long id = clienteRepository.findAll().get(0).getId();
		
		clienteService.deletePorId(id);
		
		assertEquals(0, clienteRepository.count());
	}
}
