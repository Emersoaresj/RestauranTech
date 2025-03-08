package br.com.fiap.restaurantes.gerenciamento.infra.exception.usuario;

public class UsuarioExistException extends RuntimeException {

        public UsuarioExistException(String message) {
            super(message);
        }
}
