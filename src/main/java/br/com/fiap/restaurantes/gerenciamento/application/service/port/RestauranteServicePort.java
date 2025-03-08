package br.com.fiap.restaurantes.gerenciamento.application.service.port;

import br.com.fiap.restaurantes.gerenciamento.application.dto.request.RestauranteRequest;
import br.com.fiap.restaurantes.gerenciamento.application.dto.response.MensagemResponse;
import br.com.fiap.restaurantes.gerenciamento.application.dto.response.RestauranteResponse;

import java.util.List;

public interface RestauranteServicePort {

    MensagemResponse cadastrarRestaurante(RestauranteRequest request);

    MensagemResponse atualizarRestaurante(RestauranteRequest request, Integer idRestaurante);

    List<RestauranteResponse> buscarRestaurantes();

    MensagemResponse deletarRestaurante(Integer idRestaurante);
}
