package br.com.fiap.restaurantes.gerenciamento.infra.exception;

public class UsuarioNaoEncontradoException extends RuntimeException {

        public UsuarioNaoEncontradoException(String message) {
            super(message);
        }
}
