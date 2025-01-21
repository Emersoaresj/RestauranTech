package br.com.fiap.restaurantes.gerenciamento.domain.response;

import lombok.Data;

@Data
public class AtualizarSenhaResponse {

    private String novaSenha;
    private String mensagem;

}
