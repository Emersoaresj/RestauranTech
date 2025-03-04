package br.com.fiap.restaurantes.gerenciamento.application.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TipoUsuarioDTO {

    @NotBlank(message = "O nome não pode ser vazio")
    @Size(min= 7, max = 19, message = "O tipo de usuário deve ser 'Cliente' ou 'Dono de Restaurante'")
    private String nomeTipo;

}

