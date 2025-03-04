package br.com.fiap.restaurantes.gerenciamento.infra.exception;

public class TipoUsuarioExistException extends RuntimeException {
    public TipoUsuarioExistException(String message) {
        super(message);
    }
}
