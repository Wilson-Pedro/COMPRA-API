package com.wamkti.wamk;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.wamkti.wamk.entities.Cliente;
import com.wamkti.wamk.entities.Compra;
import com.wamkti.wamk.entities.Produto;
import com.wamkti.wamk.entities.StatusCompra;
import com.wamkti.wamk.repositories.ClienteRepository;
import com.wamkti.wamk.repositories.CompraRepository;
import com.wamkti.wamk.repositories.ProdutoRepository;

@SpringBootApplication
public class CompraApplication implements CommandLineRunner{
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private CompraRepository compraRepository;

	@Autowired
	private ProdutoRepository produtoRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(CompraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		Cliente cli1 = new Cliente(null, "Wilson Pedro", 5000.0);
		Cliente cli2 = new Cliente(null, "Ana Julia", 7000.0);
		Cliente cli3 = new Cliente(null, "Julio Cezar", 4500.0);
		
		Produto p1 = new Produto(null, "Relógio", 20.0, 1, cli1);
		Produto p2 = new Produto(null, "Celular", 2500.0, 1, cli2);
		Produto p3 = new Produto(null, "Celular", 850.0, 1, cli3);
		
		cli1.getProdutos().addAll(Arrays.asList(p1));
		cli2.getProdutos().addAll(Arrays.asList(p2));
		cli3.getProdutos().addAll(Arrays.asList(p3));
		
		Compra c1 = new Compra(null, cli1, 0, 0.0, StatusCompra.COMPRANDO, null);
		Compra c2 = new Compra(null, cli2, 0, 0.0, StatusCompra.COMPRANDO, null);
		Compra c3 = new Compra(null, cli3, 0, 0.0, StatusCompra.COMPRANDO, null);
		
		clienteRepository.saveAll(Arrays.asList(cli1, cli2, cli3));
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3));
		compraRepository.saveAll(Arrays.asList(c1, c2, c3));
	}

}
