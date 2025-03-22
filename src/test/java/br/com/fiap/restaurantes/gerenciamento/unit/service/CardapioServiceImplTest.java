package br.com.fiap.restaurantes.gerenciamento.unit.service;

import br.com.fiap.restaurantes.gerenciamento.application.dto.request.CardapioRequest;
import br.com.fiap.restaurantes.gerenciamento.application.dto.response.CardapioResponse;
import br.com.fiap.restaurantes.gerenciamento.application.dto.response.MensagemResponse;
import br.com.fiap.restaurantes.gerenciamento.application.service.impl.CardapioServiceImpl;
import br.com.fiap.restaurantes.gerenciamento.domain.CardapioEntity;
import br.com.fiap.restaurantes.gerenciamento.infra.exception.ErroInternoException;
import br.com.fiap.restaurantes.gerenciamento.infra.exception.cardapio.CardapioExistsException;
import br.com.fiap.restaurantes.gerenciamento.infra.exception.cardapio.CardapioNotExistsException;
import br.com.fiap.restaurantes.gerenciamento.infra.mapper.CardapioMapper;
import br.com.fiap.restaurantes.gerenciamento.infra.repository.CardapioRepository;
import br.com.fiap.restaurantes.gerenciamento.infra.repository.UsuarioRepository;
import br.com.fiap.restaurantes.gerenciamento.utils.ConstantUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CardapioServiceImplTest {

    @InjectMocks
    private CardapioServiceImpl cardapioService;

    @Mock
    private CardapioRepository repository;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Cadastrar cardápio com sucesso")
    void cadastrarCardapioComSucesso() {
        CardapioRequest request = new CardapioRequest();
        MensagemResponse response = new MensagemResponse(ConstantUtils.CARDAPIO_CADASTRADO);

        when(repository.existsByNome(anyString())).thenReturn(false);
        when(repository.save(any(CardapioEntity.class))).thenReturn(new CardapioEntity());

        MensagemResponse result = cardapioService.cadastrarCardapio(request);

        assertEquals(response.getMensagem(), result.getMensagem());
    }

    @Test
    @DisplayName("Cadastrar cardápio com nome existente")
    void cadastrarCardapioComNomeExistente() {
        CardapioRequest request = new CardapioRequest();
        request.setNome("Pizza");

        when(repository.existsByNome(anyString())).thenReturn(true);

        Exception exception = assertThrows(CardapioExistsException.class, () -> {
            cardapioService.cadastrarCardapio(request);
        });

        assertEquals(ConstantUtils.CARDAPIO_EXISTENTE, exception.getMessage());
    }

    @Test
    @DisplayName("Erro ao cadastrar cardápio")
    void erroAoCadastrarCardapio() {
        CardapioRequest request = new CardapioRequest();

        when(repository.existsByNome(anyString())).thenReturn(false);
        when(repository.save(any(CardapioEntity.class))).thenThrow(new ErroInternoException("Erro ao cadastrar cardápio"));

        Exception exception = assertThrows(ErroInternoException.class, () -> {
            cardapioService.cadastrarCardapio(request);
        });

        assertTrue(exception.getMessage().contains("Erro ao cadastrar cardápio"));
    }

    @Test
    @DisplayName("Atualizar cardápio com sucesso")
    void atualizarCardapioComSucesso() {
        CardapioRequest request = new CardapioRequest();
        MensagemResponse response = new MensagemResponse(ConstantUtils.CARDAPIO_ATUALIZADO);

        when(repository.findById(anyInt())).thenReturn(Optional.of(new CardapioEntity()));
        when(repository.save(any(CardapioEntity.class))).thenReturn(new CardapioEntity());

        MensagemResponse result = cardapioService.atualizarCardapio(request, 1);

        assertEquals(response.getMensagem(), result.getMensagem());
    }

    @Test
    @DisplayName("Atualizar cardápio com ID inexistente")
    void atualizarCardapioComIdInexistente() {
        CardapioRequest request = new CardapioRequest();

        when(repository.findById(anyInt())).thenReturn(Optional.empty());

        Exception exception = assertThrows(CardapioNotExistsException.class, () -> {
            cardapioService.atualizarCardapio(request, 1);
        });

        assertEquals(ConstantUtils.CARDAPIO_NAO_ENCONTRADO, exception.getMessage());
    }

    @Test
    @DisplayName("Erro ao atualizar cardápio")
    void erroAoAtualizarCardapio() {
        CardapioRequest request = new CardapioRequest();

        when(repository.findById(anyInt())).thenReturn(Optional.of(new CardapioEntity()));
        when(repository.save(any(CardapioEntity.class))).thenThrow(new RuntimeException("Database error"));

        Exception exception = assertThrows(ErroInternoException.class, () -> {
            cardapioService.atualizarCardapio(request, 1);
        });

        assertTrue(exception.getMessage().contains("Erro ao atualizar cardápio"));
    }

    @Test
    @DisplayName("Buscar cardápios com sucesso")
    void buscarCardapiosComSucesso() {
        List<CardapioEntity> cardapios = List.of(new CardapioEntity(), new CardapioEntity());
        List<CardapioResponse> expectedResponses = CardapioMapper.INSTANCE.entityToResponse(cardapios);

        when(repository.findAll()).thenReturn(cardapios);

        List<CardapioResponse> result = cardapioService.buscarCardapios();

        assertEquals(expectedResponses.size(), result.size());
        assertEquals(expectedResponses, result);
    }

    @Test
    @DisplayName("Buscar cardápios com lista vazia")
    void buscarCardapiosComListaVazia() {
        when(repository.findAll()).thenReturn(Collections.emptyList());

        List<CardapioResponse> result = cardapioService.buscarCardapios();

        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Erro ao buscar cardápios")
    void erroAoBuscarCardapios() {
        when(repository.findAll()).thenThrow(new ErroInternoException("Erro ao buscar cardápios"));

        Exception exception = assertThrows(ErroInternoException.class, () -> {
            cardapioService.buscarCardapios();
        });

        assertTrue(exception.getMessage().contains("Erro ao buscar cardápios"));
    }

    @Test
    @DisplayName("Deletar cardápio com sucesso")
    void deletarCardapioComSucesso() {
        when(repository.findById(anyInt())).thenReturn(Optional.of(new CardapioEntity()));

        MensagemResponse result = cardapioService.deletarCardapio(1);

        assertEquals(ConstantUtils.CARDAPIO_DELETADO, result.getMensagem());
    }

    @Test
    @DisplayName("Deletar cardápio com ID inexistente")
    void deletarCardapioComIdInexistente() {
        when(repository.findById(anyInt())).thenReturn(Optional.empty());

        Exception exception = assertThrows(CardapioNotExistsException.class, () -> {
            cardapioService.deletarCardapio(1);
        });

        assertEquals(ConstantUtils.CARDAPIO_NAO_ENCONTRADO, exception.getMessage());
    }

    @Test
    @DisplayName("Erro ao deletar cardápio")
    void erroAoDeletarCardapio() {
        when(repository.findById(anyInt())).thenReturn(Optional.of(new CardapioEntity()));
        doThrow(new ErroInternoException("Erro ao deletar cardápio")).when(repository).deleteById(anyInt());

        Exception exception = assertThrows(ErroInternoException.class, () -> {
            cardapioService.deletarCardapio(1);
        });

        assertTrue(exception.getMessage().contains("Erro ao deletar cardápio"));
    }


}
