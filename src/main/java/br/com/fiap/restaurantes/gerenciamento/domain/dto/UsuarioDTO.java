package br.com.fiap.restaurantes.gerenciamento.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UsuarioDTO {

    @NotNull(message = "O nome não pode ser nulo")
    @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres")
    private String nome;

    @NotNull(message = "O email não pode ser nulo")
    @Email(message = "Email inválido", regexp = "^[A-Za-z0-9+_.-]+@(.+)$")
    private String email;

    @NotNull(message = "O login não pode ser nulo")
    @Size(min = 3, max = 50, message = "O login deve ter entre 3 e 50 caracteres")
    private String login;

    @NotNull(message = "A senha não pode ser nula")
    @Size(min = 6, message = "A senha deve ter pelo menos 6 caracteres")
    private String senha;

    @NotNull(message = "O endereço não pode ser nulo")
    private String endereco;

    @NotNull(message = "O tipo de usuário não pode ser nulo")
    @Size(min = 3, max = 7, message = "O tipo de usuário deve ser 'CLIENTE' ou 'DONO'")
    private String tipo;

}
