package com.wamkti.wamk.exceptions;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.wamkti.wamk.entities.Produto;
import com.wamkti.wamk.services.ProdutoService;

@SpringBootTest
class ProdutoExceptionsTest {
	
	@Autowired
	ProdutoService produtoService;

	@Test
	void ExistingProductExceptionTest() {
		produtoService.save(new Produto(null, "Relógio", 20.0, null, 10));
		
		assertThrows(ExistingProductException.class, 
				() -> produtoService.save(new Produto(null, "Relógio", 20.0, null, 10)));
	}

}
