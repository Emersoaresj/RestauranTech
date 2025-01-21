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
public class GlobalExceptionApi  extends ResponseEntityExceptionHandler {



    @ExceptionHandler(ErroBancoDeDadosException.class)
    public ResponseEntity<Map<String, Object>> handlerErroBancoDeDados(ErroBancoDeDadosException erroBancoDeDadosException, WebRequest request) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.put("error", "Erro ao salvar no Banco de Dados.");
        response.put("mensagem", erroBancoDeDadosException.getMessage());
        response.put("path", request.getDescription(false));

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(UsuarioNaoEncontradoException.class)
    public ResponseEntity<Map<String, Object>> handlerUsuarioNaoEncontrado(UsuarioNaoEncontradoException usuarioNaoEncontradoException) {
        Map<String, Object> response = new HashMap<>();

        response.put("mensagem", usuarioNaoEncontradoException.getMessage());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UsuarioExistException.class)
    public ResponseEntity<Map<String, Object>> handlerUsuarioExistente(UsuarioExistException usuarioExistException) {
        Map<String, Object> response = new HashMap<>();

        response.put("mensagem", usuarioExistException.getMessage());

        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }


    @ExceptionHandler(SenhaIncorretaException.class)
    public ResponseEntity<Map<String, Object>> handlerSenhaIncorreta(SenhaIncorretaException senhaIncorretaException) {
        Map<String, Object> response = new HashMap<>();

        response.put("mensagem", senhaIncorretaException.getMessage());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
