package br.com.fiap.restaurantes.gerenciamento.infra.exception;

public class ErroInternoException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ErroInternoException(String message) {
        super(message);
    }
}
