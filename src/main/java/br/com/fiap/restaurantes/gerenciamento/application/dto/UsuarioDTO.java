package br.com.fiap.restaurantes.gerenciamento.application.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UsuarioDTO {

    @NotBlank(message = "O campo nome é obrigatório")
    @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres")
    private String nome;

    @NotBlank(message = "O campo email é obrigatório")
    @Email(message = "Email inválido", regexp = "^[A-Za-z0-9+_.-]+@(.+)$")
    private String email;

    @NotBlank(message = "O campo login é obrigatório")
    @Size(min = 3, max = 50, message = "O login deve ter entre 3 e 50 caracteres")
    private String login;

    @NotBlank(message = "A senha não pode ser vazia")
    @Size(min = 6, message = "A senha deve ter pelo menos 6 caracteres")
    private String senha;

    @NotBlank(message = "O endereço não pode ser vazio")
    private String endereco;

    @Valid
    private TipoUsuarioDTO tipoUsuario;

}
