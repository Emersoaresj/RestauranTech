package br.com.fiap.restaurantes.gerenciamento.application.dto.request;

import lombok.Data;

@Data
public class ValidaLoginUsuarioRequest {
    private String login;
    private String senha;
}
