package br.com.fiap.restaurantes.gerenciamento.application.service.port;

import br.com.fiap.restaurantes.gerenciamento.application.dto.request.RestauranteRequest;
import br.com.fiap.restaurantes.gerenciamento.application.dto.response.RestauranteResponse;

public interface RestauranteServicePort {

    RestauranteResponse cadastrarRestaurante(RestauranteRequest request);
}
