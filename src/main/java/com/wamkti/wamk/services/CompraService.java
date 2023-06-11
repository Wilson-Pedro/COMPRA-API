package com.wamkti.wamk.services;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wamkti.wamk.dtos.CompraDTO;
import com.wamkti.wamk.entities.Compra;
import com.wamkti.wamk.repositories.CompraRepository;

@Service
public class CompraService {

	@Autowired
	private CompraRepository compraRepository;

	public List<Compra> findAll() {
		return compraRepository.findAll();
		//return list.stream().map(x -> new CompraMinDTO(x)).toList();
	}

	public Compra findById(Long compraId) {
		return compraRepository.findById(compraId).get();
	}

	public void save(Compra compra) {
		compra.setTotal(0.0);
		compraRepository.save(compra);
	}
	
	public void atualziar(Compra compra) {
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
