package br.com.fiap.restaurantes.gerenciamento.application.dto.response;

import lombok.Data;

@Data
public class AtualizarUsuarioResponse {

    private String nome;
    private String email;
    private String login;
    private String endereco;
    private String tipo;
    private String mensagem;
}
