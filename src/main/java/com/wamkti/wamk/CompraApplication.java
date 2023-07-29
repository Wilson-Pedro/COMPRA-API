package com.wamkti.wamk;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.wamkti.wamk.entities.Cliente;
import com.wamkti.wamk.entities.ItemPedido;
import com.wamkti.wamk.entities.Pagamento;
import com.wamkti.wamk.entities.PagamentoComBoleto;
import com.wamkti.wamk.entities.PagamentoComCartao;
import com.wamkti.wamk.entities.Pedido;
import com.wamkti.wamk.entities.Produto;
import com.wamkti.wamk.entities.enums.EstadoPagamento;
import com.wamkti.wamk.repositories.ClienteRepository;
import com.wamkti.wamk.repositories.ItemPedidoRepository;
import com.wamkti.wamk.repositories.PagamentoRepository;
import com.wamkti.wamk.repositories.PedidoRepository;
import com.wamkti.wamk.repositories.ProdutoRepository;

@SpringBootApplication
public class CompraApplication implements CommandLineRunner{
	
	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(CompraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		Cliente cli1 = new Cliente(null, "Wilson Pedro", 5000.0);
		Cliente cli2 = new Cliente(null, "Ana Julia", 7000.0);
		Cliente cli3 = new Cliente(null, "Julio Cezar", 4500.0);
		
		clienteRepository.saveAll(Arrays.asList(cli1, cli2, cli3));
		
		Produto p1 = new Produto(null, "Relógio", 20.0, cli1, 10);
		Produto p2 = new Produto(null, "Celular", 2500.0, cli2, 10);
		Produto p3 = new Produto(null, "Livro", 850.0, cli3, 10);
		
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3));
		
		cli1.getProdutos().addAll(Arrays.asList(p1));
		cli2.getProdutos().addAll(Arrays.asList(p2));
		cli3.getProdutos().addAll(Arrays.asList(p3));
		
		clienteRepository.saveAll(Arrays.asList(cli1, cli2, cli3));
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		Pedido ped1 = new Pedido(null, sdf.parse("30/09/2023 10:32"), cli1);
		Pedido ped2 = new Pedido(null, sdf.parse("10/10/2023 19:35"), cli2);
		Pedido ped3 = new Pedido(null, sdf.parse("20/11/2023 15:20"), cli3);
		
		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pagto1);
		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2023 19:35"), null);  
		ped2.setPagamento(pagto2);
		Pagamento pagto3 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped3, 6);
		ped3.setPagamento(pagto3);
		
		cli1.getPedidos().addAll(Arrays.asList(ped1));
		cli2.getPedidos().addAll(Arrays.asList(ped2));
		cli3.getPedidos().addAll(Arrays.asList(ped3));
		
		pedidoRepository.saveAll(Arrays.asList(ped1, ped2, ped3));
		pagamentoRepository.saveAll(Arrays.asList(pagto1, pagto2, pagto3));
		
		ItemPedido ip1 = new ItemPedido(cli1, p1, 1, 20.0);
		ItemPedido ip2 = new ItemPedido(cli2, p2, 1, 2500.0);
		ItemPedido ip3 = new ItemPedido(cli3, p3, 1, 850.0);
		
		cli1.getItens().addAll(Arrays.asList(ip1));
		cli2.getItens().addAll(Arrays.asList(ip2));
		cli3.getItens().addAll(Arrays.asList(ip3));
		
		p1.getItens().addAll(Arrays.asList(ip1));
		p2.getItens().addAll(Arrays.asList(ip2));
		p3.getItens().addAll(Arrays.asList(ip3));
		
		itemPedidoRepository.saveAll(Arrays.asList(ip1, ip2, ip3));
	}

}
