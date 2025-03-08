package br.com.fiap.restaurantes.gerenciamento.infra.exception.usuario;

public class SenhaIncorretaException extends RuntimeException {
    public SenhaIncorretaException(String message) {
        super(message);
    }
}
