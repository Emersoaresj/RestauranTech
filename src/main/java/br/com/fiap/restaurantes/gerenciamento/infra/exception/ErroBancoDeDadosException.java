package br.com.fiap.restaurantes.gerenciamento.infra.exception;

public class ErroBancoDeDadosException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ErroBancoDeDadosException(String message) {
        super(message);
    }
}
