package com.nelioalves.cursomc.resources.exceptions;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.nelioalves.cursomc.services.exceptions.AuthorizationException;
import com.nelioalves.cursomc.services.exceptions.DataIntegrityException;
import com.nelioalves.cursomc.services.exceptions.FileException;
import com.nelioalves.cursomc.services.exceptions.ObjectNotFoundException;

@ControllerAdvice
public class ResourceExceptionHandler {
	
	private static final String ERROR_NOT_FOUND = "Não encontrado"; // 404
	private static final String ERROR_DATA_INTEGRITY = "Integridade de dados";
	private static final String ERROR_VALIDATION = "Erro de Validação"; // 422 
	private static final String ERROR_ACCESS_DENIED = "Acesso Negado"; // 403
	private static final String ERROR_ARCHIVE = "Erro de Arquivo";
	
	private static final String ERROR_AMAZON_SERVICE = "Erro Amazon Service";
	private static final String ERROR_AMAZON_CLIENT = "Erro Amazon Client";
	private static final String ERROR_AMAZON_S3 = "Erro Amazon S3";
	
	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException e, HttpServletRequest request){
		
		StandardError err = new StandardError(
				System.currentTimeMillis(), 
				HttpStatus.NOT_FOUND.value(), 
				ERROR_NOT_FOUND, 
				e.getMessage(), 
				request.getRequestURI());
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
	}
	
	@ExceptionHandler(DataIntegrityException.class)
	public ResponseEntity<StandardError> dataIntegrity(DataIntegrityException e, HttpServletRequest request){
		
		StandardError err = new StandardError(
				System.currentTimeMillis(), 
				HttpStatus.BAD_REQUEST.value(), 
				ERROR_DATA_INTEGRITY, 
				e.getMessage(), 
				request.getRequestURI());
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandardError> validation(MethodArgumentNotValidException e, HttpServletRequest request){
		
		ValidationError err = new ValidationError(
				System.currentTimeMillis(), 
				HttpStatus.UNPROCESSABLE_ENTITY.value(), 
				ERROR_VALIDATION, 
				e.getMessage(), 
				request.getRequestURI());
		
		for(FieldError x: e.getBindingResult().getFieldErrors()) {
			err.addError(x.getField(), x.getDefaultMessage());
		}
		
		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(err);
	}
	
	@ExceptionHandler(AuthorizationException.class)
	public ResponseEntity<StandardError> authorization(AuthorizationException e, HttpServletRequest request){
		
		StandardError err = new StandardError(
				System.currentTimeMillis(), 
				HttpStatus.FORBIDDEN.value(), 
				ERROR_ACCESS_DENIED, 
				e.getMessage(), 
				request.getRequestURI());
		
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(err);
	}
	
	@ExceptionHandler(FileException.class)
	public ResponseEntity<StandardError> file(FileException e, HttpServletRequest request){
		
		StandardError err = new StandardError(
				System.currentTimeMillis(), 
				HttpStatus.BAD_REQUEST.value(), 
				ERROR_ARCHIVE, 
				e.getMessage(), 
				request.getRequestURI());
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}
	
	@ExceptionHandler(AmazonServiceException.class)
	public ResponseEntity<StandardError> amazonService(AmazonServiceException e, HttpServletRequest request){
		
		HttpStatus code = HttpStatus.valueOf(e.getErrorCode());
		
		StandardError err = new StandardError(
				System.currentTimeMillis(), 
				code.value(), 
				ERROR_AMAZON_SERVICE, 
				e.getMessage(), 
				request.getRequestURI());
		
		return ResponseEntity.status(code).body(err);
	}

	@ExceptionHandler(AmazonClientException.class)
	public ResponseEntity<StandardError> amazonClient(AmazonClientException e, HttpServletRequest request){
		
		StandardError err = new StandardError(
				System.currentTimeMillis(), 
				HttpStatus.BAD_REQUEST.value(), 
				ERROR_AMAZON_CLIENT, 
				e.getMessage(), 
				request.getRequestURI());
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}
	
	@ExceptionHandler(AmazonS3Exception.class)
	public ResponseEntity<StandardError> amazonS3(AmazonS3Exception e, HttpServletRequest request){
		
		StandardError err = new StandardError(
				System.currentTimeMillis(), 
				HttpStatus.BAD_REQUEST.value(), 
				ERROR_AMAZON_S3, 
				e.getMessage(), 
				request.getRequestURI());
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}
}
