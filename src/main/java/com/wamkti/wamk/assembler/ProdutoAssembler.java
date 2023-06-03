package com.wamkti.wamk.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wamkti.wamk.dtos.ProdutoDTO;
import com.wamkti.wamk.dtos.ProdutoMinDTO;
import com.wamkti.wamk.entities.Produto;

@Component
public class ProdutoAssembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public ProdutoDTO toDTO(Produto produto) {
		return modelMapper.map(produto, ProdutoDTO.class);
	}
	
	public ProdutoMinDTO toMinDTO(Produto produto) {
		var produtoMinDTO = new ProdutoMinDTO(produto);
		return modelMapper.map(produtoMinDTO, ProdutoMinDTO.class);
	}
	
	public List<ProdutoDTO> toCollectionDTO(List<Produto> produtos){
		return produtos.stream()
				.map(this::toDTO)
				.collect(Collectors.toList());
	}
	
	public List<ProdutoMinDTO> toCollectionMinDTO(List<Produto> produtos){
		return produtos.stream()
				.map(this::toMinDTO)
				.collect(Collectors.toList());
	}
	
	public Produto toEntity(ProdutoDTO produtoDTO) {
		return modelMapper.map(produtoDTO, Produto.class);
	}
}
