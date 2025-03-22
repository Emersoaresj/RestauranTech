package br.com.fiap.restaurantes.gerenciamento.infra.controller;

import br.com.fiap.restaurantes.gerenciamento.application.dto.request.CardapioRequest;
import br.com.fiap.restaurantes.gerenciamento.application.dto.response.CardapioResponse;
import br.com.fiap.restaurantes.gerenciamento.application.dto.response.MensagemResponse;
import br.com.fiap.restaurantes.gerenciamento.application.service.port.CardapioServicePort;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cardapio")
public class CardapioController {

    @Autowired
    private CardapioServicePort cardapioService;


    @PostMapping("/cadastrar")
    public ResponseEntity<MensagemResponse> cadastrarCardapio(@Valid @RequestBody CardapioRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cardapioService.cadastrarCardapio(request));
    }

    @PutMapping("/atualizar/{idCardapio}")
    public ResponseEntity<MensagemResponse> atualizarCardapio(@Valid @RequestBody CardapioRequest request,
                                                              @PathVariable Integer idCardapio) {
        return ResponseEntity.status(HttpStatus.OK).body(cardapioService.atualizarCardapio(request, idCardapio));
    }

    @GetMapping
    public ResponseEntity<List<CardapioResponse>> listarCardapios() {
        return ResponseEntity.status(HttpStatus.OK).body(cardapioService.buscarCardapios());
    }

    @DeleteMapping("/deletar/{idCardapio}")
    public ResponseEntity<MensagemResponse> deletarCardapio(@PathVariable Integer idCardapio) {
        return ResponseEntity.status(HttpStatus.OK).body(cardapioService.deletarCardapio(idCardapio));
    }
}
