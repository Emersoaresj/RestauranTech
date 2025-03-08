package br.com.fiap.restaurantes.gerenciamento.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class RestauranteRequest {

    @NotBlank(message = "Nome do restaurante é obrigatório")
    private String nomeRestaurante;
    @NotBlank(message = "Endereço do restaurante é obrigatório")
    private String enderecoRestaurante;
    @NotBlank(message = "Tipo de cozinha é obrigatório")
    private String tipoCozinha;
    @NotBlank(message = "Horário de funcionamento é obrigatório")
    @Pattern(regexp = "^([01]?[0-9]|2[0-3]):[0-5][0-9] - ([01]?[0-9]|2[0-3]):[0-5][0-9]$", message = "Horário de funcionamento inválido. Formato aceito: (00:00 - 23:59)")
    private String horarioFuncionamento;
    @NotNull(message = "ID do dono do restaurante é obrigatório")
    private Integer idDonoRestaurante;
}
