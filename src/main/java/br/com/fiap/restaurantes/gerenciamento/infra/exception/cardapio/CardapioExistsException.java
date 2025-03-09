package br.com.fiap.restaurantes.gerenciamento.infra.exception.cardapio;

public class CardapioExistsException extends RuntimeException {
    public CardapioExistsException(String message) {
        super(message);
    }
}
