package br.com.fiap.restaurantes.gerenciamento.infra.exception.restaurante;

public class RestauranteNotExistsException extends RuntimeException {
    public RestauranteNotExistsException(String message) {
        super(message);
    }
}
