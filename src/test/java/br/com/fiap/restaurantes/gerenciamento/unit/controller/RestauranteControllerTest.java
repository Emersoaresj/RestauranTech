package br.com.fiap.restaurantes.gerenciamento.unit.controller;

import br.com.fiap.restaurantes.gerenciamento.application.dto.request.RestauranteRequest;
import br.com.fiap.restaurantes.gerenciamento.application.dto.response.MensagemResponse;
import br.com.fiap.restaurantes.gerenciamento.application.dto.response.RestauranteResponse;
import br.com.fiap.restaurantes.gerenciamento.application.service.port.RestauranteServicePort;
import br.com.fiap.restaurantes.gerenciamento.infra.controller.RestauranteController;
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
public class RestauranteControllerTest {

    @InjectMocks
    private RestauranteController restauranteController;

    @Mock
    private RestauranteServicePort restauranteService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    @DisplayName("Cadastrar restaurante com sucesso")
    void cadastrarRestauranteComSucesso() {
        RestauranteRequest request = new RestauranteRequest();
        MensagemResponse response = new MensagemResponse(ConstantUtils.RESTAURANTE_CADASTRADO);

        when(restauranteService.cadastrarRestaurante(any(RestauranteRequest.class))).thenReturn(response);

        ResponseEntity<MensagemResponse> result = restauranteController.cadastrarRestaurante(request);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(response, result.getBody());
    }

    @Test
    @DisplayName("Cadastrar restaurante com dados inválidos")
    void cadastrarRestauranteComDadosInvalidos() {
        RestauranteRequest request = new RestauranteRequest();

        when(restauranteService.cadastrarRestaurante(any(RestauranteRequest.class)))
                .thenThrow(new ErroInternoException("Erro ao cadastrar restaurante"));

        Exception exception = assertThrows(ErroInternoException.class, () -> {
            restauranteController.cadastrarRestaurante(request);
        });

        assertEquals("Erro ao cadastrar restaurante", exception.getMessage());

    }

    @Test
    @DisplayName("Atualizar restaurante com sucesso")
    void atualizarRestauranteComSucesso() {
        RestauranteRequest request = new RestauranteRequest();
        MensagemResponse response = new MensagemResponse(ConstantUtils.RESTAURANTE_ATUALIZADO);

        when(restauranteService.atualizarRestaurante(any(RestauranteRequest.class), any(Integer.class))).thenReturn(response);

        ResponseEntity<MensagemResponse> result = restauranteController.atualizarRestaurante(request, 1);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
    }

    @Test
    @DisplayName("Atualizar restaurante com ID inválido")
    void atualizarRestauranteComIdInvalido() {
        RestauranteRequest request = new RestauranteRequest();

        when(restauranteService.atualizarRestaurante(any(RestauranteRequest.class), any(Integer.class)))
                .thenThrow(new ErroInternoException("Erro ao atualizar restaurante"));

        Exception exception = assertThrows(ErroInternoException.class, () -> {
            restauranteController.atualizarRestaurante(request, -1);
        });

        assertEquals("Erro ao atualizar restaurante", exception.getMessage());
    }

    @Test
    @DisplayName("Atualizar restaurante com dados inválidos")
    void atualizarRestauranteComDadosInvalidos() {
        RestauranteRequest request = new RestauranteRequest();

        when(restauranteService.atualizarRestaurante(any(RestauranteRequest.class), any(Integer.class)))
                .thenThrow(new ErroInternoException("Erro ao atualizar restaurante"));

        Exception exception = assertThrows(ErroInternoException.class, () -> {
            restauranteController.atualizarRestaurante(request, 1);
        });

        assertEquals("Erro ao atualizar restaurante", exception.getMessage());
    }

    @Test
    @DisplayName("Listar restaurantes com sucesso")
    void listarRestaurantesComSucesso() {
        List<RestauranteResponse> responseList = List.of(new RestauranteResponse(), new RestauranteResponse());

        when(restauranteService.buscarRestaurantes()).thenReturn(responseList);

        ResponseEntity<List<RestauranteResponse>> result = restauranteController.listarRestaurantes();

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(responseList, result.getBody());
    }

    @Test
    @DisplayName("Listar restaurantes quando não há restaurantes")
    void listarRestaurantesQuandoNaoHaRestaurantes() {
        List<RestauranteResponse> responseList = List.of();

        when(restauranteService.buscarRestaurantes()).thenReturn(responseList);

        ResponseEntity<List<RestauranteResponse>> result = restauranteController.listarRestaurantes();

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(responseList, result.getBody());
    }

    @Test
    @DisplayName("Erro ao listar restaurantes")
    void erroAoListarRestaurantes() {
        when(restauranteService.buscarRestaurantes()).thenThrow(new ErroInternoException("Erro ao listar restaurantes"));

        Exception exception = assertThrows(ErroInternoException.class, () -> {
            restauranteController.listarRestaurantes();
        });

        assertEquals("Erro ao listar restaurantes", exception.getMessage());
    }

    @Test
    @DisplayName("Deletar restaurante com sucesso")
    void deletarRestauranteComSucesso() {
        MensagemResponse response = new MensagemResponse(ConstantUtils.RESTAURANTE_DELETADO);

        when(restauranteService.deletarRestaurante(any(Integer.class))).thenReturn(response);

        ResponseEntity<MensagemResponse> result = restauranteController.deletarRestaurante(1);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
    }

    @Test
    @DisplayName("Deletar restaurante com ID inválido")
    void deletarRestauranteComIdInvalido() {
        when(restauranteService.deletarRestaurante(any(Integer.class)))
                .thenThrow(new ErroInternoException("Erro ao deletar restaurante"));

        Exception exception = assertThrows(ErroInternoException.class, () -> {
            restauranteController.deletarRestaurante(-1);
        });

        assertEquals("Erro ao deletar restaurante", exception.getMessage());
    }

    @Test
    @DisplayName("Erro ao deletar restaurante")
    void erroAoDeletarRestaurante() {
        when(restauranteService.deletarRestaurante(any(Integer.class)))
                .thenThrow(new ErroInternoException("Erro ao deletar restaurante"));

        Exception exception = assertThrows(ErroInternoException.class, () -> {
            restauranteController.deletarRestaurante(1);
        });

        assertEquals("Erro ao deletar restaurante", exception.getMessage());
    }
}
