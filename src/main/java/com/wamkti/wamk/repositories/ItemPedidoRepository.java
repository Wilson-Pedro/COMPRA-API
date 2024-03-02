package com.wamkti.wamk.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wamkti.wamk.entities.ItemPedido;
import com.wamkti.wamk.entities.ItemPedidoPK;

public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long>{

	Optional<ItemPedido> findById(ItemPedidoPK itemPk);
	
	boolean existsById(ItemPedidoPK itemPk);
}
