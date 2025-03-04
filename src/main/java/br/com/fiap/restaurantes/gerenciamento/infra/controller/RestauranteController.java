package br.com.fiap.restaurantes.gerenciamento.infra.controller;

import br.com.fiap.restaurantes.gerenciamento.application.dto.RestauranteDTO;
import br.com.fiap.restaurantes.gerenciamento.application.dto.request.RestauranteRequest;
import br.com.fiap.restaurantes.gerenciamento.application.dto.response.RestauranteResponse;
import br.com.fiap.restaurantes.gerenciamento.application.service.port.RestauranteServicePort;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

    @Autowired
    private RestauranteServicePort restauranteService;

    @PostMapping("/cadastrar")
    public ResponseEntity<RestauranteResponse> cadastrarRestaurante(@Valid @RequestBody RestauranteRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(restauranteService.cadastrarRestaurante(request));
    }
}
