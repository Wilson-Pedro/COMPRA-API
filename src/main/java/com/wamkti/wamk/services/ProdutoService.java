package com.wamkti.wamk.services;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wamkti.wamk.dtos.ProdutoDTO;
import com.wamkti.wamk.entities.Produto;
import com.wamkti.wamk.repositories.ProdutoRepository;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;

	public List<ProdutoDTO> findAllDTO() {
		//return list.stream().map(x -> new ProdutoMinDTO(x)).toList();
		List<Produto> list = produtoRepository.findAll();
		return list.stream().map(x -> new ProdutoDTO(x)).toList();
	}

	public Produto findById(Long produtoId) {
		return produtoRepository.findById(produtoId).get();
	}
	
	public ProdutoDTO findByIdDTO(Long produtoId) {
		Produto prouduto = produtoRepository.findById(produtoId).get();
		return new ProdutoDTO(prouduto);
	}
	
	@Transactional
	public Produto save(Produto produto) {
		return produtoRepository.save(produto);
	}

	@Transactional
	public void deletePorId(Long produtoId) {
		produtoRepository.deleteById(produtoId);
	}
	
	@Transactional
	public Produto copiarESalvar(ProdutoDTO produtoDTO) {
		var produto = new Produto();
		BeanUtils.copyProperties(produtoDTO, produto);
		produtoRepository.save(produto);
		return produto;
	}

	@Transactional
	public void atualizar(ProdutoDTO produtoDTO, Long produtoId) {
		var produto = new Produto();
		BeanUtils.copyProperties(produtoDTO, produto);
		produto.setId(produtoId);
		produtoRepository.save(produto);
	}
}
