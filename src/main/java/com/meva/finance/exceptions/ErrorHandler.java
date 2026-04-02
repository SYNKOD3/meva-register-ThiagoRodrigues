package com.meva.finance.exceptions;

import com.meva.finance.response.ValidationErrorData;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class ErrorHandler {

    // 1. Trata erro 404 (ID não existe)
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handle404(EntityNotFoundException ex) {
        return ResponseEntity.status(404).body("Usuário não encontrado no sistema.");
    }

    // 2. Trata erro 400 (Falha nas anotações @NotBlank, @Past, @Pattern, etc.)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ValidationErrorData>> handle400(MethodArgumentNotValidException ex) {
        var errors = ex.getFieldErrors().stream().map(ValidationErrorData::new).toList();
        return ResponseEntity.badRequest().body(errors);
    }

    // 3. Trata erro 400 (JSON inválido, erro de sintaxe ou tipo de dado errado)
    @ExceptionHandler(org.springframework.http.converter.HttpMessageNotReadableException.class)
    public ResponseEntity<String> handle400Readable(org.springframework.http.converter.HttpMessageNotReadableException ex) {
        return ResponseEntity.badRequest().body("Erro na leitura do JSON: Verifique a sintaxe ou o formato dos campos (ex: datas ou sexo).");
    }

    // 5. Trata erro 409 (Conflict, usuário já existe)
    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<String> handle409(EntityExistsException ex) {
        return ResponseEntity.status(409).body(ex.getMessage());
    }

    // 5. Trata qualquer outro erro 400 de parâmetros de URL
    @ExceptionHandler(org.springframework.web.bind.MissingServletRequestParameterException.class)
    public ResponseEntity<String> handle400MissingParam(org.springframework.web.bind.MissingServletRequestParameterException ex) {
        return ResponseEntity.badRequest().body("Parâmetro obrigatório ausente: " + ex.getParameterName());
    }
}