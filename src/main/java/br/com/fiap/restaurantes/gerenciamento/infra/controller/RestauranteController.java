package br.com.fiap.restaurantes.gerenciamento.infra.controller;

import br.com.fiap.restaurantes.gerenciamento.application.dto.request.RestauranteRequest;
import br.com.fiap.restaurantes.gerenciamento.application.dto.response.MensagemResponse;
import br.com.fiap.restaurantes.gerenciamento.application.dto.response.RestauranteResponse;
import br.com.fiap.restaurantes.gerenciamento.application.service.port.RestauranteServicePort;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

    @Autowired
    private RestauranteServicePort restauranteService;

    @PostMapping("/cadastrar")
    public ResponseEntity<MensagemResponse> cadastrarRestaurante(@Valid @RequestBody RestauranteRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(restauranteService.cadastrarRestaurante(request));
    }

    @PutMapping("/atualizar/{idRestaurante}")
    public ResponseEntity<MensagemResponse> atualizarRestaurante(@Valid @RequestBody RestauranteRequest request,
                                                                 @PathVariable Integer idRestaurante){
        return ResponseEntity.status(HttpStatus.OK).body(restauranteService.atualizarRestaurante(request, idRestaurante));
    }

    @GetMapping
    public ResponseEntity<List<RestauranteResponse>> listarRestaurantes(){
        return ResponseEntity.status(HttpStatus.OK).body(restauranteService.buscarRestaurantes());
    }

    @DeleteMapping("/deletar/{idRestaurante}")
    public ResponseEntity<MensagemResponse> deletarRestaurante(@PathVariable Integer idRestaurante){
        return ResponseEntity.status(HttpStatus.OK).body(restauranteService.deletarRestaurante(idRestaurante));
    }
}
