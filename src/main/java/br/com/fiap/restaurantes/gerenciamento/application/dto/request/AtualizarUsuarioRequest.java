package br.com.fiap.restaurantes.gerenciamento.application.dto.request;

import lombok.Data;

@Data
public class AtualizarUsuarioRequest {

    private String nome;
    private String email;
    private String login;
    private String endereco;
    private String tipo;
}
