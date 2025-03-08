package br.com.fiap.restaurantes.gerenciamento.infra.exception.restaurante;

public class RestauranteExistsException extends RuntimeException {
    public RestauranteExistsException(String message) {
        super(message);
    }
}
