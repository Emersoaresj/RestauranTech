package br.com.fiap.restaurantes.gerenciamento.unit.service;

import br.com.fiap.restaurantes.gerenciamento.application.dto.request.RestauranteRequest;
import br.com.fiap.restaurantes.gerenciamento.application.dto.response.MensagemResponse;
import br.com.fiap.restaurantes.gerenciamento.application.dto.response.RestauranteResponse;
import br.com.fiap.restaurantes.gerenciamento.application.service.impl.RestauranteServiceImpl;
import br.com.fiap.restaurantes.gerenciamento.domain.RestauranteEntity;
import br.com.fiap.restaurantes.gerenciamento.domain.UsuarioEntity;
import br.com.fiap.restaurantes.gerenciamento.infra.exception.ErroInternoException;
import br.com.fiap.restaurantes.gerenciamento.infra.exception.restaurante.RestauranteExistsException;
import br.com.fiap.restaurantes.gerenciamento.infra.exception.restaurante.RestauranteNotExistsException;
import br.com.fiap.restaurantes.gerenciamento.infra.exception.usuario.UsuarioNotExistsException;
import br.com.fiap.restaurantes.gerenciamento.infra.mapper.RestauranteMapper;
import br.com.fiap.restaurantes.gerenciamento.infra.repository.RestauranteRepository;
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
public class RestauranteServiceImplTest {

    @InjectMocks
    private RestauranteServiceImpl restauranteService;

    @Mock
    private RestauranteRepository repository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Cadastrar restaurante com sucesso")
    void cadastrarRestauranteComSucesso() {
        RestauranteRequest request = new RestauranteRequest();
        request.setIdDonoRestaurante(1);
        MensagemResponse response = new MensagemResponse(ConstantUtils.RESTAURANTE_CADASTRADO);

        when(usuarioRepository.findById(anyInt())).thenReturn(Optional.of(new UsuarioEntity()));
        when(repository.existsByNomeRestaurante(anyString())).thenReturn(false);
        when(repository.save(any(RestauranteEntity.class))).thenReturn(new RestauranteEntity());

        MensagemResponse result = restauranteService.cadastrarRestaurante(request);

        assertEquals(response.getMensagem(), result.getMensagem());
    }

    @Test
    @DisplayName("Cadastrar restaurante com dono inexistente")
    void cadastrarRestauranteComDonoInexistente() {
        RestauranteRequest request = new RestauranteRequest();

        when(usuarioRepository.findById(anyInt())).thenReturn(Optional.empty());

        Exception exception = assertThrows(UsuarioNotExistsException.class, () -> {
            restauranteService.cadastrarRestaurante(request);
        });

        assertEquals(ConstantUtils.DONO_NAO_ENCONTRADO, exception.getMessage());
    }

    @Test
    @DisplayName("Cadastrar restaurante com nome existente")
    void cadastrarRestauranteComNomeExistente() {
        RestauranteRequest request = new RestauranteRequest();
        request.setIdDonoRestaurante(1);
        request.setNomeRestaurante("RestauranTech");

        when(usuarioRepository.findById(anyInt())).thenReturn(Optional.of(new UsuarioEntity()));
        when(repository.existsByNomeRestaurante(anyString())).thenReturn(true);

        Exception exception = assertThrows(RestauranteExistsException.class, () -> {
            restauranteService.cadastrarRestaurante(request);
        });

        assertEquals(ConstantUtils.RESTAURANTE_EXISTENTE, exception.getMessage());
    }

    @Test
    @DisplayName("Erro ao cadastrar restaurante")
    void erroAoCadastrarRestaurante() {
        RestauranteRequest request = new RestauranteRequest();
        request.setIdDonoRestaurante(1);
        request.setNomeRestaurante("RestauranTech");

        when(usuarioRepository.findById(anyInt())).thenReturn(Optional.of(new UsuarioEntity()));
        when(repository.existsByNomeRestaurante(anyString())).thenReturn(false);
        when(repository.save(any(RestauranteEntity.class))).thenThrow(new ErroInternoException("Erro ao cadastrar restaurante"));

        Exception exception = assertThrows(ErroInternoException.class, () -> {
            restauranteService.cadastrarRestaurante(request);
        });

        assertTrue(exception.getMessage().contains("Erro ao cadastrar restaurante"));
    }


    @Test
    @DisplayName("Atualizar restaurante com sucesso")
    void atualizarRestauranteComSucesso() {
        RestauranteRequest request = new RestauranteRequest();
        request.setIdDonoRestaurante(1);
        MensagemResponse response = new MensagemResponse(ConstantUtils.RESTAURANTE_ATUALIZADO);

        when(repository.findById(anyInt())).thenReturn(Optional.of(new RestauranteEntity()));
        when(usuarioRepository.findById(anyInt())).thenReturn(Optional.of(new UsuarioEntity()));
        when(repository.save(any(RestauranteEntity.class))).thenReturn(new RestauranteEntity());

        MensagemResponse result = restauranteService.atualizarRestaurante(request, 1);

        assertEquals(response.getMensagem(), result.getMensagem());
    }

    @Test
    @DisplayName("Atualizar restaurante com ID inexistente")
    void atualizarRestauranteComIdInexistente() {
        RestauranteRequest request = new RestauranteRequest();

        when(repository.findById(anyInt())).thenReturn(Optional.empty());

        Exception exception = assertThrows(RestauranteNotExistsException.class, () -> {
            restauranteService.atualizarRestaurante(request, 1);
        });

        assertEquals(ConstantUtils.RESTAURANTE_NAO_ENCONTRADO, exception.getMessage());
    }

    @Test
    @DisplayName("Atualizar restaurante com dono inexistente")
    void atualizarRestauranteComDonoInexistente() {
        RestauranteRequest request = new RestauranteRequest();
        request.setIdDonoRestaurante(1);

        when(repository.findById(anyInt())).thenReturn(Optional.of(new RestauranteEntity()));
        when(usuarioRepository.findById(anyInt())).thenReturn(Optional.empty());

        Exception exception = assertThrows(UsuarioNotExistsException.class, () -> {
            restauranteService.atualizarRestaurante(request, 1);
        });

        assertEquals(ConstantUtils.DONO_NAO_ENCONTRADO, exception.getMessage());
    }

    @Test
    @DisplayName("Erro ao atualizar restaurante")
    void erroAoAtualizarRestaurante() {
        RestauranteRequest request = new RestauranteRequest();
        request.setIdDonoRestaurante(1);

        when(repository.findById(anyInt())).thenReturn(Optional.of(new RestauranteEntity()));
        when(usuarioRepository.findById(anyInt())).thenReturn(Optional.of(new UsuarioEntity()));
        when(repository.save(any(RestauranteEntity.class))).thenThrow(new RuntimeException("Database error"));

        Exception exception = assertThrows(ErroInternoException.class, () -> {
            restauranteService.atualizarRestaurante(request, 1);
        });

        assertTrue(exception.getMessage().contains("Erro ao atualizar restaurante"));
    }

    @Test
    @DisplayName("Buscar restaurantes com sucesso")
    void buscarRestaurantesComSucesso() {
        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity.setNome("Emerson");
        List<RestauranteEntity> restaurantes = List.of(new RestauranteEntity(), new RestauranteEntity());
        restaurantes.stream().forEach(r -> r.setDonoRestaurante(usuarioEntity));

        List<RestauranteResponse> expectedResponses = RestauranteMapper.INSTANCE.entityToResponse(restaurantes);

        when(repository.findAll()).thenReturn(restaurantes);

        List<RestauranteResponse> result = restauranteService.buscarRestaurantes();

        assertEquals(expectedResponses.size(), result.size());
        assertEquals(expectedResponses, result);
    }

    @Test
    @DisplayName("Buscar restaurantes com lista vazia")
    void buscarRestaurantesComListaVazia() {
        when(repository.findAll()).thenReturn(Collections.emptyList());

        List<RestauranteResponse> result = restauranteService.buscarRestaurantes();

        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Erro ao buscar restaurantes")
    void erroAoBuscarRestaurantes() {
        when(repository.findAll()).thenThrow(new ErroInternoException("Erro ao buscar restaurantes"));

        Exception exception = assertThrows(ErroInternoException.class, () -> {
            restauranteService.buscarRestaurantes();
        });

        assertTrue(exception.getMessage().contains("Erro ao buscar restaurantes"));
    }

    @Test
    @DisplayName("Deletar restaurante com sucesso")
    void deletarRestauranteComSucesso() {
        when(repository.findById(anyInt())).thenReturn(Optional.of(new RestauranteEntity()));

        MensagemResponse result = restauranteService.deletarRestaurante(1);

        assertEquals(ConstantUtils.RESTAURANTE_DELETADO, result.getMensagem());
    }

    @Test
    @DisplayName("Deletar restaurante com ID inexistente")
    void deletarRestauranteComIdInexistente() {
        when(repository.findById(anyInt())).thenReturn(Optional.empty());

        Exception exception = assertThrows(RestauranteNotExistsException.class, () -> {
            restauranteService.deletarRestaurante(1);
        });

        assertEquals(ConstantUtils.RESTAURANTE_NAO_ENCONTRADO, exception.getMessage());
    }

    @Test
    @DisplayName("Erro ao deletar restaurante")
    void erroAoDeletarRestaurante() {
        when(repository.findById(anyInt())).thenReturn(Optional.of(new RestauranteEntity()));
        doThrow(new ErroInternoException("Erro ao deletar restaurante")).when(repository).delete(any(RestauranteEntity.class));

        Exception exception = assertThrows(ErroInternoException.class, () -> {
            restauranteService.deletarRestaurante(1);
        });

        assertTrue(exception.getMessage().contains("Erro ao deletar restaurante"));
    }

}
