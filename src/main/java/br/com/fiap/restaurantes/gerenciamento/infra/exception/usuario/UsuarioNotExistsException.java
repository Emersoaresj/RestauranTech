package br.com.fiap.restaurantes.gerenciamento.infra.exception.usuario;

public class UsuarioNotExistsException extends RuntimeException {

        public UsuarioNotExistsException(String message) {
            super(message);
        }
}
