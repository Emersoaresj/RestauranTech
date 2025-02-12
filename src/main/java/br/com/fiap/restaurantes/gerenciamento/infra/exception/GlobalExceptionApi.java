package br.com.fiap.restaurantes.gerenciamento.infra.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice // Controlador de exceções
public class GlobalExceptionApi extends ResponseEntityExceptionHandler {

    // Constantes para as chaves de resposta
    private static final String TIMESTAMP = "timestamp";
    private static final String STATUS = "status";
    private static final String ERROR = "error";
    private static final String MENSAGEM = "mensagem";
    private static final String PATH = "path";

    @ExceptionHandler(ErroBancoDeDadosException.class)
    public ResponseEntity<Map<String, Object>> handlerErroBancoDeDados(ErroBancoDeDadosException erroBancoDeDadosException, WebRequest request) {
        Map<String, Object> response = new HashMap<>();
        response.put(TIMESTAMP, LocalDateTime.now());
        response.put(STATUS, HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.put(ERROR, "Erro ao salvar no Banco de Dados.");
        response.put(MENSAGEM, erroBancoDeDadosException.getMessage());
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
}