package com.example.algamoney.api.exceptionhandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class AlgamoneyExceptionHandler extends ResponseEntityExceptionHandler {
//Handler, para capturar excessões de respsota das entidades	

	@Autowired
	private MessageSource messageSource;

	@Override // Metodo de envio de mensagens de erro
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		String mensagemUsuario = messageSource.getMessage("mensagem.invalida", null, LocaleContextHolder.getLocale()); // vai
																														// a
																														// mensagem
		String mensagemDesenvolvedor = ex.getCause().toString(); // vai a exceção // a

		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));

		return handleExceptionInternal(ex, erros, headers, HttpStatus.BAD_REQUEST, request); // mensagem tratada, mesmo
																								// que só retorne um,
																								// vai retornar uma
																								// lista.
	}
	
	
	@ExceptionHandler({ EmptyResultDataAccessException.class })
	public ResponseEntity<Object> handleEmptyEmptyResultDataAccessException(EmptyResultDataAccessException ex,
			WebRequest request) {

		String mensagemUsuario = messageSource.getMessage("recurso.nao-encontrado", null,
				LocaleContextHolder.getLocale()); // vai a mensagem
		String mensagemDesenvolvedor = ex.toString(); // vai a exceção

		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));
		return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
		
	} 
	

	@Override // metodo que trata quando o argumento de um metodo não está valido
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		List<Erro> erros = ListaDeErros(ex.getBindingResult());
		return handleExceptionInternal(ex, erros, headers, HttpStatus.BAD_REQUEST, request);
	}
    
	// Lista de erros, para listar os erros em todos os atributos da classe
	public List<Erro> ListaDeErros(BindingResult bindingResult) { // BindingResult tem a lista de todos os erros
		List<Erro> erros = new ArrayList<>();

		for (FieldError fieldError : bindingResult.getFieldErrors()) { // Field = campos, logo ele pega todos os erros
																		// que aconteceram nos atributos
			String mensagemUsuario = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
			String mensagemDesenvolvedor = fieldError.toString();

			// adicionando a lista de erros.
			erros.add(new Erro(mensagemUsuario, mensagemDesenvolvedor));
		}
		
		return erros;
	}

	public static class Erro {

		private String mensagemUsuario;
		private String mensagemDesenvolvedor;

		public Erro(String mensagemUsuario, String mensagemDesenvolvedor) {
			this.mensagemUsuario = mensagemUsuario;
			this.mensagemDesenvolvedor = mensagemDesenvolvedor;
		}

		public String getMensagemUsuario() {
			return mensagemUsuario;
		}

		public String getMensagemDesenvolvedor() {
			return mensagemDesenvolvedor;
		}

	}

}
