package com.wamkti.wamk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wamkti.wamk.entities.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long>{

}
