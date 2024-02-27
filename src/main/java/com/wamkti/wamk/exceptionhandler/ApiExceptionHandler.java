package com.wamkti.wamk.exceptionhandler;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.wamkti.wamk.exceptions.EntityNotFoundException;
import com.wamkti.wamk.exceptions.ExistingProductException;
import com.wamkti.wamk.exceptions.InsufficientBalanceException;
import com.wamkti.wamk.exceptions.InsufficientStockException;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler{

	@Autowired
	private MessageSource messageSource;
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		List<Campo> campos = new ArrayList<>();
		
		for(ObjectError error : ex.getBindingResult().getAllErrors()) {
			String nome = ((FieldError)error).getField();
			String menssagem = messageSource.getMessage(error, LocaleContextHolder.getLocale());
			
			campos.add(new Campo(nome, menssagem));
		}
		
		Problema problema = new Problema();
		problema.setTitulo("Um ou mais campos estão inválidos! Por favor preencha-os corretamente");
		problema.setDataHora(OffsetDateTime.now());
		problema.setStatus(status.value());
		problema.setCampos(campos);
		
		return handleExceptionInternal(ex, problema, headers, status, request);
	}
	
	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<Problema> entityNotFoundException(){
		
		Problema problema = new Problema();
		
		HttpStatus status = HttpStatus.NOT_FOUND;

		problema.setStatus(status.value());
		problema.setTitulo("Entity not found");
		problema.setDataHora(OffsetDateTime.now());
		
		return ResponseEntity.status(status).body(problema);
	}
	
	@ExceptionHandler(InsufficientBalanceException.class)
	public ResponseEntity<Problema> insufficientBalanceException(){
		
		Problema problema = new Problema();
		
		HttpStatus status = HttpStatus.BAD_REQUEST;

		problema.setStatus(status.value());
		problema.setTitulo("Entity not found");
		problema.setDataHora(OffsetDateTime.now());
		
		return ResponseEntity.status(status).body(problema);
	}
	
	@ExceptionHandler(InsufficientStockException.class)
	public ResponseEntity<Problema> insufficientStockException(){
		
		Problema problema = new Problema();
		
		HttpStatus status = HttpStatus.BAD_REQUEST;

		problema.setStatus(status.value());
		problema.setTitulo("Entity not found");
		problema.setDataHora(OffsetDateTime.now());
		
		return ResponseEntity.status(status).body(problema);
	}
	
	@ExceptionHandler(ExistingProductException.class)
	public ResponseEntity<Problema> existingProductException(){
		
		Problema problema = new Problema();
		
		HttpStatus status = HttpStatus.BAD_REQUEST;

		problema.setStatus(status.value());
		problema.setTitulo("Entity not found");
		problema.setDataHora(OffsetDateTime.now());
		
		return ResponseEntity.status(status).body(problema);
	}
}
