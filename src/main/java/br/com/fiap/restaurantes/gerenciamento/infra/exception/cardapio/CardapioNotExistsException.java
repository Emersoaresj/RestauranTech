package br.com.fiap.restaurantes.gerenciamento.infra.exception.cardapio;

public class CardapioNotExistsException extends RuntimeException {
    public CardapioNotExistsException(String message) {
        super(message);
    }
}
