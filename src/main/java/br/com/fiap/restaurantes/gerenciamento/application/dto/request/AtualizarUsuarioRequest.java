package br.com.fiap.restaurantes.gerenciamento.application.dto.request;

import br.com.fiap.restaurantes.gerenciamento.application.dto.TipoUsuarioDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AtualizarUsuarioRequest {

    @NotBlank(message = "O campo nome é obrigatório")
    private String nome;
    @NotBlank(message = "O campo email é obrigatório")
    @Email(message = "Email inválido", regexp = "^[A-Za-z0-9+_.-]+@(.+)$")
    private String email;
    @NotBlank(message = "O campo login é obrigatório")
    @Size(min = 3, max = 50, message = "O login deve ter entre 3 e 50 caracteres")
    private String login;
    @NotBlank(message = "O campo endereco é obrigatório")
    private String endereco;
    @Valid
    private TipoUsuarioDTO tipoUsuario;
}
