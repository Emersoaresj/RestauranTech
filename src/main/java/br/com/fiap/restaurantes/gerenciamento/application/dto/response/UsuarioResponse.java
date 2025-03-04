package br.com.fiap.restaurantes.gerenciamento.application.dto.response;

import br.com.fiap.restaurantes.gerenciamento.application.dto.TipoUsuarioDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UsuarioResponse {

    private Long id;
    private String nome;
    private String email;
    private String login;
    private TipoUsuarioDTO tipoUsuario;
    private String mensagem;
}