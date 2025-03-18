package br.com.fiap.restaurantes.gerenciamento.controller;

import br.com.fiap.restaurantes.gerenciamento.application.dto.UsuarioDTO;
import br.com.fiap.restaurantes.gerenciamento.application.dto.request.AlterarSenhaRequest;
import br.com.fiap.restaurantes.gerenciamento.application.dto.request.AtualizarUsuarioRequest;
import br.com.fiap.restaurantes.gerenciamento.application.dto.request.ValidaLoginUsuarioRequest;
import br.com.fiap.restaurantes.gerenciamento.application.dto.response.AtualizarSenhaResponse;
import br.com.fiap.restaurantes.gerenciamento.application.dto.response.MensagemResponse;
import br.com.fiap.restaurantes.gerenciamento.application.dto.response.UsuarioResponse;
import br.com.fiap.restaurantes.gerenciamento.application.dto.response.ValidaLoginUsuarioResponse;
import br.com.fiap.restaurantes.gerenciamento.application.service.port.UsuarioServicePort;
import br.com.fiap.restaurantes.gerenciamento.infra.controller.UsuarioController;
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
public class UsuarioControllerTest {

    @InjectMocks
    private UsuarioController usuarioController;

    @Mock
    private UsuarioServicePort usuarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Listar usuários com sucesso")
    void listarUsuariosComSucesso() {
        List<UsuarioResponse> responseList = List.of(new UsuarioResponse(), new UsuarioResponse());

        when(usuarioService.listarUsuarios()).thenReturn(responseList);

        ResponseEntity<List<UsuarioResponse>> result = usuarioController.listarUsuarios();

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(responseList, result.getBody());
    }

    @Test
    @DisplayName("Listar usuários quando não há usuários")
    void listarUsuariosQuandoNaoHaUsuarios() {
        List<UsuarioResponse> responseList = List.of();

        when(usuarioService.listarUsuarios()).thenReturn(responseList);

        ResponseEntity<List<UsuarioResponse>> result = usuarioController.listarUsuarios();

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(responseList, result.getBody());
    }

    @Test
    @DisplayName("Erro ao listar usuários")
    void erroAoListarUsuarios() {
        when(usuarioService.listarUsuarios()).thenThrow(new ErroInternoException("Erro ao listar usuários"));

        Exception exception = assertThrows(ErroInternoException.class, () -> {
            usuarioController.listarUsuarios();
        });

        assertEquals("Erro ao listar usuários", exception.getMessage());
    }


    @Test
    @DisplayName("Cadastrar usuário com sucesso")
    void cadastrarUsuarioComSucesso() {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        MensagemResponse response = new MensagemResponse(ConstantUtils.USUARIO_CADASTRADO);

        when(usuarioService.cadastrarUsuario(any(UsuarioDTO.class))).thenReturn(response);

        ResponseEntity<MensagemResponse> result = usuarioController.cadastrarUsuario(usuarioDTO);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(response, result.getBody());
    }

    @Test
    @DisplayName("Cadastrar usuário com dados inválidos")
    void cadastrarUsuarioComDadosInvalidos() {
        UsuarioDTO usuarioDTO = new UsuarioDTO();

        when(usuarioService.cadastrarUsuario(any(UsuarioDTO.class)))
                .thenThrow(new ErroInternoException("Erro ao cadastrar usuário"));

        Exception exception = assertThrows(ErroInternoException.class, () -> {
            usuarioController.cadastrarUsuario(usuarioDTO);
        });

        assertEquals("Erro ao cadastrar usuário", exception.getMessage());
    }

    @Test
    @DisplayName("Atualizar usuário com sucesso")
    void atualizarUsuarioComSucesso() {
        AtualizarUsuarioRequest request = new AtualizarUsuarioRequest();
        MensagemResponse response = new MensagemResponse(ConstantUtils.USUARIO_ATUALIZADO);

        when(usuarioService.atualizarUsuario(any(Integer.class), any(AtualizarUsuarioRequest.class))).thenReturn(response);

        ResponseEntity<MensagemResponse> result = usuarioController.atualizarUsuario(1, request);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
    }

    @Test
    @DisplayName("Atualizar usuário com ID inválido")
    void atualizarUsuarioComIdInvalido() {
        AtualizarUsuarioRequest request = new AtualizarUsuarioRequest();

        when(usuarioService.atualizarUsuario(any(Integer.class), any(AtualizarUsuarioRequest.class)))
                .thenThrow(new ErroInternoException("Erro ao atualizar usuário"));

        Exception exception = assertThrows(ErroInternoException.class, () -> {
            usuarioController.atualizarUsuario(-1, request);
        });

        assertEquals("Erro ao atualizar usuário", exception.getMessage());
    }

    @Test
    @DisplayName("Atualizar usuário com dados inválidos")
    void atualizarUsuarioComDadosInvalidos() {
        AtualizarUsuarioRequest request = new AtualizarUsuarioRequest();

        when(usuarioService.atualizarUsuario(any(Integer.class), any(AtualizarUsuarioRequest.class)))
                .thenThrow(new ErroInternoException("Erro ao atualizar usuário"));

        Exception exception = assertThrows(ErroInternoException.class, () -> {
            usuarioController.atualizarUsuario(1, request);
        });

        assertEquals("Erro ao atualizar usuário", exception.getMessage());
    }

    @Test
    @DisplayName("Alterar senha com sucesso")
    void alterarSenhaComSucesso() {
        AlterarSenhaRequest request = new AlterarSenhaRequest();
        AtualizarSenhaResponse response = new AtualizarSenhaResponse();
        response.setNovaSenha("novaSenha");
        response.setMensagem(ConstantUtils.SENHA_ALTERADA);

        when(usuarioService.alterarSenha(any(Integer.class), any(AlterarSenhaRequest.class))).thenReturn(response);

        ResponseEntity<AtualizarSenhaResponse> result = usuarioController.alterarSenha(1, request);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
    }

    @Test
    @DisplayName("Alterar senha com ID inválido")
    void alterarSenhaComIdInvalido() {
        AlterarSenhaRequest request = new AlterarSenhaRequest();

        when(usuarioService.alterarSenha(any(Integer.class), any(AlterarSenhaRequest.class)))
                .thenThrow(new ErroInternoException("Erro ao alterar senha"));

        Exception exception = assertThrows(ErroInternoException.class, () -> {
            usuarioController.alterarSenha(-1, request);
        });

        assertEquals("Erro ao alterar senha", exception.getMessage());
    }

    @Test
    @DisplayName("Erro ao alterar senha")
    void erroAoAlterarSenha() {
        AlterarSenhaRequest request = new AlterarSenhaRequest();

        when(usuarioService.alterarSenha(any(Integer.class), any(AlterarSenhaRequest.class)))
                .thenThrow(new ErroInternoException("Erro ao alterar senha"));

        Exception exception = assertThrows(ErroInternoException.class, () -> {
            usuarioController.alterarSenha(1, request);
        });

        assertEquals("Erro ao alterar senha", exception.getMessage());
    }

    @Test
    @DisplayName("Validar login com sucesso")
    void validarLoginComSucesso() {
        ValidaLoginUsuarioRequest request = new ValidaLoginUsuarioRequest();
        ValidaLoginUsuarioResponse response = new ValidaLoginUsuarioResponse();

        when(usuarioService.validarLogin(any(ValidaLoginUsuarioRequest.class))).thenReturn(response);

        ResponseEntity<ValidaLoginUsuarioResponse> result = usuarioController.validarLogin(request);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
    }

    @Test
    @DisplayName("Validar login com dados inválidos")
    void validarLoginComDadosInvalidos() {
        ValidaLoginUsuarioRequest request = new ValidaLoginUsuarioRequest();

        when(usuarioService.validarLogin(any(ValidaLoginUsuarioRequest.class)))
                .thenThrow(new ErroInternoException("Erro ao validar login"));

        Exception exception = assertThrows(ErroInternoException.class, () -> {
            usuarioController.validarLogin(request);
        });

        assertEquals("Erro ao validar login", exception.getMessage());
    }

    @Test
    @DisplayName("Deletar usuário com sucesso")
    void deletarUsuarioComSucesso() {
        MensagemResponse response = new MensagemResponse(ConstantUtils.USUARIO_DELETADO);

        when(usuarioService.deletarUsuario(any(Integer.class))).thenReturn(response);

        ResponseEntity<MensagemResponse> result = usuarioController.deletarUsuario(1);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
    }

    @Test
    @DisplayName("Deletar usuário com ID inválido")
    void deletarUsuarioComIdInvalido() {
        when(usuarioService.deletarUsuario(any(Integer.class)))
                .thenThrow(new ErroInternoException("Erro ao deletar usuário"));

        Exception exception = assertThrows(ErroInternoException.class, () -> {
            usuarioController.deletarUsuario(-1);
        });

        assertEquals("Erro ao deletar usuário", exception.getMessage());
    }

    @Test
    @DisplayName("Erro ao deletar usuário")
    void erroAoDeletarUsuario() {
        when(usuarioService.deletarUsuario(any(Integer.class)))
                .thenThrow(new ErroInternoException("Erro ao deletar usuário"));

        Exception exception = assertThrows(ErroInternoException.class, () -> {
            usuarioController.deletarUsuario(1);
        });

        assertEquals("Erro ao deletar usuário", exception.getMessage());
    }

}
