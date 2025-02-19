package br.com.fiap.restaurantes.gerenciamento.application.dto.response;

import lombok.Data;

@Data
public class AtualizarSenhaResponse {

    private String novaSenha;
    private String mensagem;

}
