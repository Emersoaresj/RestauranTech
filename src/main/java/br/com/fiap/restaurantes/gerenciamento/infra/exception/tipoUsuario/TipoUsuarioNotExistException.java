package br.com.fiap.restaurantes.gerenciamento.infra.exception.tipoUsuario;

public class TipoUsuarioNotExistException extends RuntimeException {
    public TipoUsuarioNotExistException(String message) {
        super(message);
    }
}
