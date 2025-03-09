package br.com.fiap.restaurantes.gerenciamento.application.service.port;

import br.com.fiap.restaurantes.gerenciamento.application.dto.request.CardapioRequest;
import br.com.fiap.restaurantes.gerenciamento.application.dto.response.CardapioResponse;
import br.com.fiap.restaurantes.gerenciamento.application.dto.response.MensagemResponse;

import java.util.List;

public interface CardapioServicePort {

    MensagemResponse cadastrarCardapio(CardapioRequest request);

    MensagemResponse atualizarCardapio(CardapioRequest request, Integer idCardapio);

    List<CardapioResponse> buscarCardapios();

    MensagemResponse deletarCardapio(Integer idCardapio);
}
