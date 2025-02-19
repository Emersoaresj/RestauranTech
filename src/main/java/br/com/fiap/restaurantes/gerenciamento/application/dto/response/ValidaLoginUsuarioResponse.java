package br.com.fiap.restaurantes.gerenciamento.application.dto.response;

import lombok.Data;

@Data
public class ValidaLoginUsuarioResponse {

    private String login;
    private String mensagem;

}
