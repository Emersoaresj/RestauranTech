package br.com.fiap.restaurantes.gerenciamento.infra.exception;

public class UsuarioExistException extends RuntimeException {

        public UsuarioExistException(String message) {
            super(message);
        }
}
