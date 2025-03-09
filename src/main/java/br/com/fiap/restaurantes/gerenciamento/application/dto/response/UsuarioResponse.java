package br.com.fiap.restaurantes.gerenciamento.application.dto.response;

import br.com.fiap.restaurantes.gerenciamento.application.dto.TipoUsuarioDTO;
import lombok.Data;

@Data
public class UsuarioResponse {

    private Integer id;
    private String nome;
    private String email;
    private String login;
    private TipoUsuarioDTO tipoUsuario;
}