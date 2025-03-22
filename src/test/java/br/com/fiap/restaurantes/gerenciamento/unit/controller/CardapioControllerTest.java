package br.com.fiap.restaurantes.gerenciamento.unit.controller;

import br.com.fiap.restaurantes.gerenciamento.application.dto.request.CardapioRequest;
import br.com.fiap.restaurantes.gerenciamento.application.dto.response.CardapioResponse;
import br.com.fiap.restaurantes.gerenciamento.application.dto.response.MensagemResponse;
import br.com.fiap.restaurantes.gerenciamento.application.service.port.CardapioServicePort;
import br.com.fiap.restaurantes.gerenciamento.infra.controller.CardapioController;
import br.com.fiap.restaurantes.gerenciamento.infra.exception.ErroInternoException;
import br.com.fiap.restaurantes.gerenciamento.utils.ConstantUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CardapioControllerTest {

    @Mock
    private CardapioServicePort cardapioService;

    @InjectMocks
    private CardapioController cardapioController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Cadastrar cardápio com sucesso")
    void cadastrarCardapioComSucesso() {
        CardapioRequest request = new CardapioRequest();
        MensagemResponse response = new MensagemResponse(ConstantUtils.CARDAPIO_CADASTRADO);

        when(cardapioService.cadastrarCardapio(any(CardapioRequest.class))).thenReturn(response);

        ResponseEntity<MensagemResponse> result = cardapioController.cadastrarCardapio(request);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(response, result.getBody());
    }

    @Test
    @DisplayName("Cadastrar cardápio com dados inválidos")
    void cadastrarCardapioComDadosInvalidos() {
        CardapioRequest request = new CardapioRequest();

        when(cardapioService.cadastrarCardapio(any(CardapioRequest.class)))
                .thenThrow(new ErroInternoException("Erro ao cadastrar cardápio"));

        Exception exception = assertThrows(ErroInternoException.class, () -> {
            cardapioController.cadastrarCardapio(request);
        });

        assertEquals("Erro ao cadastrar cardápio", exception.getMessage());
    }

    @Test
    @DisplayName("Atualizar cardápio com sucesso")
    void atualizarCardapioComSucesso() {
        CardapioRequest request = new CardapioRequest();
        MensagemResponse response = new MensagemResponse(ConstantUtils.CARDAPIO_ATUALIZADO);

        when(cardapioService.atualizarCardapio(any(CardapioRequest.class), any(Integer.class))).thenReturn(response);

        ResponseEntity<MensagemResponse> result = cardapioController.atualizarCardapio(request, 1);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
    }

    @Test
    @DisplayName("Atualizar cardápio com ID inválido")
    void atualizarCardapioComIdInvalido() {
        CardapioRequest request = new CardapioRequest();

        when(cardapioService.atualizarCardapio(any(CardapioRequest.class), any(Integer.class)))
                .thenThrow(new ErroInternoException("Erro ao atualizar cardápio"));

        Exception exception = assertThrows(ErroInternoException.class, () -> {
            cardapioController.atualizarCardapio(request, -1);
        });

        assertEquals("Erro ao atualizar cardápio", exception.getMessage());
    }

    @Test
    @DisplayName("Atualizar cardápio com dados inválidos")
    void atualizarCardapioComDadosInvalidos() {
        CardapioRequest request = new CardapioRequest();

        when(cardapioService.atualizarCardapio(any(CardapioRequest.class), any(Integer.class)))
                .thenThrow(new ErroInternoException("Erro ao atualizar cardápio"));

        Exception exception = assertThrows(ErroInternoException.class, () -> {
            cardapioController.atualizarCardapio(request, 1);
        });

        assertEquals("Erro ao atualizar cardápio", exception.getMessage());
    }

    @Test
    @DisplayName("Listar cardápios com sucesso")
    void listarCardapiosComSucesso() {
        List<CardapioResponse> responseList = List.of(new CardapioResponse(), new CardapioResponse());

        when(cardapioService.buscarCardapios()).thenReturn(responseList);

        ResponseEntity<List<CardapioResponse>> result = cardapioController.listarCardapios();

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(responseList, result.getBody());
    }

    @Test
    @DisplayName("Listar cardápios quando não há cardápios")
    void listarCardapiosQuandoNaoHaCardapios() {
        List<CardapioResponse> responseList = List.of();

        when(cardapioService.buscarCardapios()).thenReturn(responseList);

        ResponseEntity<List<CardapioResponse>> result = cardapioController.listarCardapios();

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(responseList, result.getBody());
    }

    @Test
    @DisplayName("Erro ao listar cardápios")
    void erroAoListarCardapios() {
        when(cardapioService.buscarCardapios()).thenThrow(new ErroInternoException("Erro ao listar cardápios"));

        Exception exception = assertThrows(ErroInternoException.class, () -> {
            cardapioController.listarCardapios();
        });

        assertEquals("Erro ao listar cardápios", exception.getMessage());
    }


    @Test
    @DisplayName("Deletar cardápio com sucesso")
    void deletarCardapioComSucesso() {
        MensagemResponse response = new MensagemResponse(ConstantUtils.CARDAPIO_DELETADO);

        when(cardapioService.deletarCardapio(any(Integer.class))).thenReturn(response);

        ResponseEntity<MensagemResponse> result = cardapioController.deletarCardapio(1);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
    }

    @Test
    @DisplayName("Deletar cardápio com ID inválido")
    void deletarCardapioComIdInvalido() {
        when(cardapioService.deletarCardapio(any(Integer.class)))
                .thenThrow(new ErroInternoException("Erro ao deletar cardápio"));

        Exception exception = assertThrows(ErroInternoException.class, () -> {
            cardapioController.deletarCardapio(-1);
        });

        assertEquals("Erro ao deletar cardápio", exception.getMessage());
    }

    @Test
    @DisplayName("Erro ao deletar cardápio")
    void erroAoDeletarCardapio() {
        when(cardapioService.deletarCardapio(any(Integer.class)))
                .thenThrow(new ErroInternoException("Erro ao deletar cardápio"));

        Exception exception = assertThrows(ErroInternoException.class, () -> {
            cardapioController.deletarCardapio(1);
        });

        assertEquals("Erro ao deletar cardápio", exception.getMessage());
    }

}
