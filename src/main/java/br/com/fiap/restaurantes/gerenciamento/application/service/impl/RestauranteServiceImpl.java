package br.com.fiap.restaurantes.gerenciamento.application.service.impl;

import br.com.fiap.restaurantes.gerenciamento.application.dto.request.RestauranteRequest;
import br.com.fiap.restaurantes.gerenciamento.application.dto.response.RestauranteResponse;
import br.com.fiap.restaurantes.gerenciamento.application.service.port.RestauranteServicePort;
import org.springframework.stereotype.Service;

@Service
public class RestauranteServiceImpl implements RestauranteServicePort {


    @Override
    public RestauranteResponse cadastrarRestaurante(RestauranteRequest request) {
        return null;
    }
}
