package com.wamkti.wamk.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wamkti.wamk.dtos.CompreDTO;
import com.wamkti.wamk.dtos.ProdutoCompradoDTO;
import com.wamkti.wamk.entities.Produto;
import com.wamkti.wamk.exceptions.EntityNotFoundException;
import com.wamkti.wamk.exceptions.ExistingProductException;
import com.wamkti.wamk.exceptions.InsufficientBalanceException;
import com.wamkti.wamk.exceptions.InsufficientStockException;
import com.wamkti.wamk.projections.ProdutoMinProjection;
import com.wamkti.wamk.repositories.ProdutoRepository;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private ItemPedidoService itemPedidoService;
	
	@Autowired
	private ClienteService clienteService;

	@Transactional
	public List<Produto> findAll() {
		return produtoRepository.findAll();
	}

	@Transactional
	public Produto findById(Long produtoId) {
		return produtoRepository.findById(produtoId)
				.orElseThrow(() -> new EntityNotFoundException());
	}
	
	@Transactional
	public Produto save(Produto produto) {
		if(produtoRepository.existsByNomeProduto(produto.getNomeProduto())) 
			throw new ExistingProductException();
		produto.setId(null);
		return produtoRepository.save(produto);
	}

	@Transactional
	public void deletePorId(Long produtoId) {
		produtoRepository.delete(produtoRepository.findById(produtoId)
				.orElseThrow(() -> new EntityNotFoundException()));
	}

	@Transactional
	public Produto atualizar(Produto produto, Long produtoId) {
		produto.setId(produtoId);
		return produtoRepository.save(produto);
	}

	@Transactional
	public Produto atulizarClienteIdDoPedido(Long clienteId, Long produtoId) {
		var cliente = clienteService.findById(clienteId);
		var produto = produtoRepository.findById(produtoId);
		produto.get().setCliente(cliente);
		produto.get().setId(produtoId);
		return produtoRepository.save(produto.get());
	}
	
	@Transactional(readOnly = true)
	public List<ProdutoCompradoDTO> findByCliente(Long clienteId){
		List<ProdutoMinProjection> list = produtoRepository.searchByList(clienteId);
		return list.stream().map(x -> new ProdutoCompradoDTO(x)).toList();
	}

	public Produto atualizarEstoque(Integer quantidadeCompra, Long produtoId) {
		var produto = produtoRepository.findById(produtoId).get();
		Integer estoque = produto.getEstoque();
		produto.setEstoque(estoque - quantidadeCompra);
		produto.setId(produtoId);
		return produtoRepository.save(produto);
	}

	@Transactional
	public void comprar(CompreDTO compreDTO) {
		
		var cliente = clienteService.findById(compreDTO.getClienteId());
		var produto = findById(compreDTO.getProdutoId());
		
		double subtotal = produto.getPreco() * compreDTO.getQuantidade();
		double dinheiroClinte = cliente.getDinheiro();
		
		if(dinheiroClinte == 0 || subtotal > dinheiroClinte) 
			throw new InsufficientBalanceException();
		else if (compreDTO.getQuantidade() > produto.getEstoque())
			throw new InsufficientStockException();
		
		clienteService.atualizarDinheiro(cliente, subtotal);
		
		atualizarEstoque(compreDTO.getQuantidade(), compreDTO.getProdutoId());
		atulizarClienteIdDoPedido(compreDTO.getClienteId(), compreDTO.getProdutoId());
		
		itemPedidoService.criarOuAtulizarItemPedido(cliente, produto, compreDTO.getQuantidade());
	}

}
