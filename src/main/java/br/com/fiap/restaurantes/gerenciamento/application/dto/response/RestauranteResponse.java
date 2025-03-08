package br.com.fiap.restaurantes.gerenciamento.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RestauranteResponse {

    private String nomeRestaurante;
    private String enderecoRestaurante;
    private String tipoCozinha;
    private String horarioFuncionamento;
    private String donoRestaurante;
}
