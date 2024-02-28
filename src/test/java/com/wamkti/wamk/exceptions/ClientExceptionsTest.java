package com.wamkti.wamk.exceptions;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.wamkti.wamk.services.ClienteService;

@SpringBootTest
class ClientExceptionsTest {
	
	@Autowired
	ClienteService clienteService;

	@Test
	void EntityNotFoundExceptionTest() {
		
		assertThrows(EntityNotFoundException.class, () -> clienteService.findById(70L));
	}

}
