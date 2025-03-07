package br.com.fiap.restaurantes.gerenciamento.infra.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice // Controlador de exceções
public class GlobalExceptionApi {

    // Constantes para as chaves de resposta
    private static final String TIMESTAMP = "timestamp";
    private static final String STATUS = "status";
    private static final String ERROR = "error";
    private static final String MENSAGEM = "mensagem";
    private static final String PATH = "path";


    @ExceptionHandler(TipoUsuarioNotExistException.class)
    public ResponseEntity<Map<String, Object>> handlerTipoUsuarioNaoExistente(TipoUsuarioNotExistException tipoUsuarioNotExistException) {
        Map<String, Object> response = new HashMap<>();
        response.put(MENSAGEM, tipoUsuarioNotExistException.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ErroInternoException.class)
    public ResponseEntity<Map<String, Object>> handlerErroBancoDeDados(ErroInternoException erroInternoException, WebRequest request) {
        Map<String, Object> response = new HashMap<>();
        response.put(TIMESTAMP, LocalDateTime.now());
        response.put(STATUS, HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.put(ERROR, "Erro ao salvar no Banco de Dados.");
        response.put(MENSAGEM, erroInternoException.getMessage());
        response.put(PATH, request.getDescription(false));

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UsuarioNaoEncontradoException.class)
    public ResponseEntity<Map<String, Object>> handlerUsuarioNaoEncontrado(UsuarioNaoEncontradoException usuarioNaoEncontradoException) {
        Map<String, Object> response = new HashMap<>();
        response.put(MENSAGEM, usuarioNaoEncontradoException.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UsuarioExistException.class)
    public ResponseEntity<Map<String, Object>> handlerUsuarioExistente(UsuarioExistException usuarioExistException) {
        Map<String, Object> response = new HashMap<>();
        response.put(MENSAGEM, usuarioExistException.getMessage());
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(SenhaIncorretaException.class)
    public ResponseEntity<Map<String, Object>> handlerSenhaIncorreta(SenhaIncorretaException senhaIncorretaException) {
        Map<String, Object> response = new HashMap<>();
        response.put(MENSAGEM, senhaIncorretaException.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex, WebRequest request) {
        Map<String, Object> response = new HashMap<>();

        // Extrair mensagens de erro das validações
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> fieldError.getDefaultMessage())
                .collect(Collectors.toList());

        response.put("errors", errors);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}