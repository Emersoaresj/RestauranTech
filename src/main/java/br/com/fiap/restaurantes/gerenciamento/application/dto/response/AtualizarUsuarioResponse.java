package br.com.fiap.restaurantes.gerenciamento.application.dto.response;

import br.com.fiap.restaurantes.gerenciamento.application.dto.TipoUsuarioDTO;
import lombok.Data;

@Data
public class AtualizarUsuarioResponse {

    private String nome;
    private String email;
    private String login;
    private String endereco;
    private TipoUsuarioDTO tipo;
    private String mensagem;
}
