package com.wamkti.wamk.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.wamkti.wamk.dtos.ClienteDTO;
import com.wamkti.wamk.dtos.CompreDTO;
import com.wamkti.wamk.dtos.min.ClienteMinDTO;
import com.wamkti.wamk.entities.Cliente;
import com.wamkti.wamk.repositories.ClienteRepository;
import com.wamkti.wamk.services.ClienteService;
import com.wamkti.wamk.services.ItemPedidoService;
import com.wamkti.wamk.services.ProdutoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private ItemPedidoService itemPedidoService;

	@GetMapping
	public ResponseEntity<List<ClienteMinDTO>> listar() {
		List<ClienteMinDTO> listDto = clienteService.findAll()
				.stream()
				.map(x -> new ClienteMinDTO(x)).toList();
		if(!listDto.isEmpty()) 
			for(ClienteMinDTO cliente : listDto) {
				cliente.add(linkTo(methodOn(ClienteController.class).buscarPorId(cliente.getId())).withSelfRel());
			}
		return ResponseEntity.ok(listDto);
	}
	
//	@GetMapping("/page")
//	public ResponseEntity<Page<ClienteDTO>> paginar(Pageable pageable) {
//		Page<Cliente> pages = clienteRepository.findAll(pageable);
//		Page<ClienteDTO> pagesDto = pages.map(ClienteDTO::new);
//		return ResponseEntity.ok(pagesDto);
//	}
	
	@GetMapping(value = "/{clienteId}")
	public ResponseEntity<ClienteDTO> buscarPorId(@PathVariable Long clienteId) {
		Cliente cliente = clienteService.findById(clienteId);
		var clienteDto = new ClienteDTO(cliente);
		clienteDto.add(linkTo(methodOn(ClienteController.class).listar()).withSelfRel());
		return ResponseEntity.ok(clienteDto);
	}
	
	@PostMapping
	public ResponseEntity<ClienteDTO> adcionarCliente(@RequestBody @Valid ClienteDTO clienteDTO) {
		Cliente cliente = clienteService.salvar(new Cliente(clienteDTO));
		return ResponseEntity.status(HttpStatus.CREATED).body(new ClienteDTO(cliente));
	}
	
	@PutMapping(value = "/{clienteId}")
	public ResponseEntity<Void> atualizarCliente(@RequestBody @Valid ClienteDTO clienteDTO, 
			@PathVariable Long clienteId) {
		clienteService.atualizar(new Cliente(clienteDTO), clienteId);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping(value = "/{clienteId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletarCliente(@PathVariable Long clienteId) {
		clienteService.deletePorId(clienteId);
	}
	
	@PostMapping("/compras")
	public ResponseEntity<Object> clienteCompraProduto(@RequestBody @Valid CompreDTO compreDTO) {
		
		produtoService.comprar(compreDTO);
		
//		Long clienteId = compreDTO.getClienteId();
//		
//		var cliente = clienteRepository.findById(clienteId).get();
//		var produto = produtoService.findById(compreDTO.getProdutoId());
//		
//		Integer quantidade = compreDTO.getQuantidade();
//		
//		double subtotal = produto.getPreco() * quantidade;
//		double dinheiroClinte = cliente.getDinheiro();
//		
//		if(dinheiroClinte == 0 || subtotal > dinheiroClinte) {
//			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Você não possui saldo suficiente para fazer esta compra");
//		} else if (compreDTO.getQuantidade() > produto.getEstoque()) {
//			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
//					"Estoque insuficiente."
//					+ "\nQuantidade demandada: " + compreDTO.getQuantidade()
//					+ "\nQuantidade em estoque: " + produto.getEstoque());
//		}
//		
//
//		clienteService.atualizarDinheiro(cliente, subtotal);
//		produtoService.atualizarEstoque(compreDTO.getQuantidade(), compreDTO.getProdutoId());
//		produtoService.atulizarClienteIdDoPedido(clienteId, compreDTO.getProdutoId());
//		itemPedidoService.criarOuAtulizarItemPedido(cliente, produto, compreDTO.getQuantidade());
		
		return ResponseEntity.ok("Compra realizado com sucesso");
	}
}
