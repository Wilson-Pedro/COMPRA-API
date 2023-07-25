package com.wamkti.wamk.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wamkti.wamk.entities.Cliente;
import com.wamkti.wamk.entities.ItemPedido;
import com.wamkti.wamk.entities.ItemPedidoPK;
import com.wamkti.wamk.entities.Produto;
import com.wamkti.wamk.repositories.ItemPedidoRepository;

@Service
public class ItemPedidoService {

	@Autowired
	private ItemPedidoRepository itemPedidoRepository;

	public void criarOuAtulizarItemPedido(Cliente cliente, Produto produto, Integer quantidade) {
		ItemPedidoPK itemPk = new ItemPedidoPK(cliente, produto);
		Optional<ItemPedido> ip = itemPedidoRepository.findById(itemPk);
		if(ip.isPresent()) {
			ip.get().setQuantidade(ip.get().getQuantidade() + quantidade);
			itemPedidoRepository.save(ip.get());
		} else {
			ItemPedido newIp = new ItemPedido(cliente, produto, quantidade, produto.getPreco());
			itemPedidoRepository.save(newIp);
		}

	}
}
