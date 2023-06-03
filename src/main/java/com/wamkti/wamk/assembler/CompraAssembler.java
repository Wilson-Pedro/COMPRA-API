package com.wamkti.wamk.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wamkti.wamk.dtos.CompraDTO;
import com.wamkti.wamk.dtos.CompraMinDTO;
import com.wamkti.wamk.entities.Compra;

@Component
public class CompraAssembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public CompraDTO toDTO(Compra compra) {
		return modelMapper.map(compra, CompraDTO.class);
	}
	
	public CompraMinDTO toMinDTO(Compra compra) {
		var compraMinDTO = new CompraMinDTO(compra);
		return modelMapper.map(compraMinDTO, CompraMinDTO.class);
	}
	
	public List<CompraDTO> toCollectionDTO(List<Compra> compras){
		return compras.stream()
				.map(this::toDTO)
				.collect(Collectors.toList());
	}
	
	public List<CompraMinDTO> toCollectionMinDTO(List<Compra> compras){
		return compras.stream()
				.map(this::toMinDTO)
				.collect(Collectors.toList());
	}
	
	public Compra toEntity(CompraDTO compraDTO) {
		return modelMapper.map(compraDTO, Compra.class);
	}
}
