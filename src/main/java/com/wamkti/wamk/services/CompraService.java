package com.wamkti.wamk.services;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wamkti.wamk.dtos.CompraDTO;
import com.wamkti.wamk.dtos.CompraMinDTO;
import com.wamkti.wamk.entities.Compra;
import com.wamkti.wamk.entities.StatusCompra;
import com.wamkti.wamk.repositories.CompraRepository;

@Service
public class CompraService {

	@Autowired
	private CompraRepository compraRepository;
	
	
	public List<CompraMinDTO> findAll() {
		//return compraRepository.findAll();
		List<Compra> list = compraRepository.findAll();
		return list.stream().map(x -> new CompraMinDTO(x)).toList();
	}

	public Compra findById(Long compraId) {
		return compraRepository.findById(compraId).get();
	}
	
	public CompraDTO findByIdDTO(Long compraId) {
		Compra compra = compraRepository.findById(compraId).get();
		return new CompraDTO(compra);
	}

	public void save(Compra compra) {
		compraRepository.save(compra);
	}
	
	public void atualziar(Compra compra, int items, double subtotal) {
		compra.setItems(compra.getItems() + items);
		compra.setTotal(compra.getTotal() + subtotal);
		compraRepository.save(compra);
	}

	public void deletePorId(Long compraId) {
		compraRepository.deleteById(compraId);
		
	}

	@Transactional
	public Compra copiarESalvar(CompraDTO compraDTO) {
		var compra = new Compra();
		BeanUtils.copyProperties(compraDTO, compra);
		compraRepository.save(compra);
		return compra;
	}

	@Transactional
	public void atualizarComDTO(CompraDTO compraDTO, Long compraId) {
		var compra = new Compra();
		BeanUtils.copyProperties(compraDTO, compra);
		compra.setId(compraId);
		compraRepository.save(compra);
	}
}
