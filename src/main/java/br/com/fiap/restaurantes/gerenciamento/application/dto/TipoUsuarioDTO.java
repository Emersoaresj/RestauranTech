package br.com.fiap.restaurantes.gerenciamento.application.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TipoUsuarioDTO {

    @NotBlank(message = "O nome não pode ser vazio")
    @Pattern(regexp = "(?i)Cliente|Dono de Restaurante", message = "O tipo de usuário deve ser 'Cliente' ou 'Dono de Restaurante'")
    private String nomeTipo;

}

