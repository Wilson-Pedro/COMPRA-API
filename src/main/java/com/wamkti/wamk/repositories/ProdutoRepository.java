package com.wamkti.wamk.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.wamkti.wamk.entities.Produto;
import com.wamkti.wamk.projections.ProdutoMinProjection;

public interface ProdutoRepository extends JpaRepository<Produto, Long>{

	@Query(nativeQuery = true, value =""" 
			SELECT tbl_produto.id, tbl_produto.nome_produto AS nomeProduto, 
			tbl_produto.PRECO, tbl_produto.QUANTIDADE 
			FROM tbl_produto 
			INNER JOIN tbl_cliente ON tbl_cliente.id = tbl_produto.cliente_id
			WHERE tbl_cliente.id = :clienteId
			ORDER BY preco
			""")
	List<ProdutoMinProjection> searchByList(Long clienteId);
	
	boolean existsByNomeProduto(String nomeProduto);
}
