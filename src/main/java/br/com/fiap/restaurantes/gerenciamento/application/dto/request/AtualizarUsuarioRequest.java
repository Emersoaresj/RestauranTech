package br.com.fiap.restaurantes.gerenciamento.application.dto.request;

import br.com.fiap.restaurantes.gerenciamento.application.dto.TipoUsuarioDTO;
import lombok.Data;

@Data
public class AtualizarUsuarioRequest {

    private String nome;
    private String email;
    private String login;
    private String endereco;
    private TipoUsuarioDTO tipo;
}
