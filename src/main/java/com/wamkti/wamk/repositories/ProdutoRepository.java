package com.wamkti.wamk.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.wamkti.wamk.entities.Produto;
import com.wamkti.wamk.projections.ProdutoMinProjection;

public interface ProdutoRepository extends JpaRepository<Produto, Long>{

	@Query(nativeQuery = true, value =""" 
				SELECT PR.id, PR.nome_produto AS nomeProduto, IP.preco, IP.quantidade 
				FROM TB_ITEMPEDIDO IP 
				INNER JOIN TBL_PRODUTO PR 
				ON PR.id = IP.produto_id 
				INNER JOIN TBL_CLIENTE CL 
				ON CL.id = IP.cliente_id
				WHERE CL.id = :clienteId
			""")
	List<ProdutoMinProjection> searchByList(@Param("clienteId") Long clienteId);
	
	boolean existsByNomeProduto(String nomeProduto);
}
