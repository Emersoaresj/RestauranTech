package br.com.fiap.restaurantes.gerenciamento.unit.service;

import br.com.fiap.restaurantes.gerenciamento.application.dto.TipoUsuarioDTO;
import br.com.fiap.restaurantes.gerenciamento.application.dto.UsuarioDTO;
import br.com.fiap.restaurantes.gerenciamento.application.dto.request.AlterarSenhaRequest;
import br.com.fiap.restaurantes.gerenciamento.application.dto.request.AtualizarUsuarioRequest;
import br.com.fiap.restaurantes.gerenciamento.application.dto.request.ValidaLoginUsuarioRequest;
import br.com.fiap.restaurantes.gerenciamento.application.dto.response.AtualizarSenhaResponse;
import br.com.fiap.restaurantes.gerenciamento.application.dto.response.MensagemResponse;
import br.com.fiap.restaurantes.gerenciamento.application.dto.response.UsuarioResponse;
import br.com.fiap.restaurantes.gerenciamento.application.dto.response.ValidaLoginUsuarioResponse;
import br.com.fiap.restaurantes.gerenciamento.application.service.impl.UsuarioServiceImpl;
import br.com.fiap.restaurantes.gerenciamento.application.service.port.TipoUsuarioServicePort;
import br.com.fiap.restaurantes.gerenciamento.domain.TipoUsuarioEntity;
import br.com.fiap.restaurantes.gerenciamento.domain.UsuarioEntity;
import br.com.fiap.restaurantes.gerenciamento.infra.exception.ErroInternoException;
import br.com.fiap.restaurantes.gerenciamento.infra.exception.tipoUsuario.TipoUsuarioNotExistException;
import br.com.fiap.restaurantes.gerenciamento.infra.exception.usuario.SenhaIncorretaException;
import br.com.fiap.restaurantes.gerenciamento.infra.exception.usuario.UsuarioExistException;
import br.com.fiap.restaurantes.gerenciamento.infra.exception.usuario.UsuarioNotExistsException;
import br.com.fiap.restaurantes.gerenciamento.infra.mapper.UsuarioMapper;
import br.com.fiap.restaurantes.gerenciamento.infra.repository.UsuarioRepository;
import br.com.fiap.restaurantes.gerenciamento.utils.ConstantUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UsuarioServiceImplTest {

    @InjectMocks
    private UsuarioServiceImpl usuarioService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private TipoUsuarioServicePort tipoUsuarioService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Cadastrar usuário com sucesso")
    void cadastrarUsuarioComSucesso() {

        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setLogin("usuario");
        usuarioDTO.setTipoUsuario(new TipoUsuarioDTO("Admin"));
        MensagemResponse response = new MensagemResponse(ConstantUtils.USUARIO_CADASTRADO);

        when(usuarioRepository.existsByLogin(anyString())).thenReturn(false);
        when(tipoUsuarioService.buscarTipoUsuarioPorNome(anyString())).thenReturn(new TipoUsuarioEntity());
        when(usuarioRepository.save(any(UsuarioEntity.class))).thenReturn(new UsuarioEntity());

        MensagemResponse result = usuarioService.cadastrarUsuario(usuarioDTO);

        assertEquals(response.getMensagem(), result.getMensagem());
    }

    @Test
    @DisplayName("Cadastrar usuário com login existente")
    void cadastrarUsuarioComLoginExistente() {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setLogin("usuarioExistente");

        when(usuarioRepository.existsByLogin(anyString())).thenReturn(true);

        Exception exception = assertThrows(UsuarioExistException.class, () -> {
            usuarioService.cadastrarUsuario(usuarioDTO);
        });

        assertEquals(ConstantUtils.LOGIN_CADASTRADO, exception.getMessage());
    }

    @Test
    @DisplayName("Cadastrar usuário com tipo de usuário inexistente")
    void cadastrarUsuarioComTipoUsuarioInexistente() {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setLogin("usuario");
        usuarioDTO.setTipoUsuario(new TipoUsuarioDTO("ADMINISTRADOR"));

        when(usuarioRepository.existsByLogin(anyString())).thenReturn(false);
        when(tipoUsuarioService.buscarTipoUsuarioPorNome(anyString())).thenThrow(new TipoUsuarioNotExistException(ConstantUtils.TIPO_USUARIO_NAO_ENCONTRADO));

        Exception exception = assertThrows(TipoUsuarioNotExistException.class, () -> {
            usuarioService.cadastrarUsuario(usuarioDTO);
        });

        assertEquals(ConstantUtils.TIPO_USUARIO_NAO_ENCONTRADO, exception.getMessage());
    }

    @Test
    @DisplayName("Erro ao cadastrar usuário")
    void erroAoCadastrarUsuario() {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setLogin("usuario");
        usuarioDTO.setTipoUsuario(new TipoUsuarioDTO("Admin"));

        when(usuarioRepository.existsByLogin(anyString())).thenReturn(false);
        when(tipoUsuarioService.buscarTipoUsuarioPorNome(anyString())).thenReturn(new TipoUsuarioEntity());
        when(usuarioRepository.save(any(UsuarioEntity.class))).thenThrow(new ErroInternoException("Erro ao cadastrar usuário"));

        Exception exception = assertThrows(ErroInternoException.class, () -> {
            usuarioService.cadastrarUsuario(usuarioDTO);
        });

        assertTrue(exception.getMessage().contains("Erro ao cadastrar usuário"));
    }

    @Test
    @DisplayName("Atualizar usuário com sucesso")
    void atualizarUsuarioComSucesso() {
        AtualizarUsuarioRequest request = new AtualizarUsuarioRequest();
        request.setTipoUsuario(new TipoUsuarioDTO("Admin"));
        UsuarioEntity usuarioEntity = new UsuarioEntity();
        TipoUsuarioEntity tipoUsuario = new TipoUsuarioEntity();
        MensagemResponse response = new MensagemResponse(ConstantUtils.USUARIO_ATUALIZADO);

        when(usuarioRepository.findById(anyInt())).thenReturn(Optional.of(usuarioEntity));
        when(tipoUsuarioService.buscarTipoUsuarioPorNome(anyString())).thenReturn(tipoUsuario);
        when(usuarioRepository.save(any(UsuarioEntity.class))).thenReturn(usuarioEntity);

        MensagemResponse result = usuarioService.atualizarUsuario(1, request);

        assertEquals(response.getMensagem(), result.getMensagem());
    }

    @Test
    @DisplayName("Atualizar usuário com ID inexistente")
    void atualizarUsuarioComIdInexistente() {
        AtualizarUsuarioRequest request = new AtualizarUsuarioRequest();

        when(usuarioRepository.findById(anyInt())).thenReturn(Optional.empty());

        Exception exception = assertThrows(UsuarioNotExistsException.class, () -> {
            usuarioService.atualizarUsuario(1, request);
        });

        assertEquals(ConstantUtils.USUARIO_NAO_ENCONTRADO, exception.getMessage());
    }

    @Test
    @DisplayName("Atualizar usuário com tipo de usuário inexistente")
    void atualizarUsuarioComTipoUsuarioInexistente() {
        AtualizarUsuarioRequest request = new AtualizarUsuarioRequest();
        request.setTipoUsuario(new TipoUsuarioDTO("NaoExiste"));
        UsuarioEntity usuarioEntity = new UsuarioEntity();

        when(usuarioRepository.findById(anyInt())).thenReturn(Optional.of(usuarioEntity));
        when(tipoUsuarioService.buscarTipoUsuarioPorNome(anyString())).thenThrow(new TipoUsuarioNotExistException(ConstantUtils.TIPO_USUARIO_NAO_ENCONTRADO));

        Exception exception = assertThrows(TipoUsuarioNotExistException.class, () -> {
            usuarioService.atualizarUsuario(1, request);
        });

        assertEquals(ConstantUtils.TIPO_USUARIO_NAO_ENCONTRADO, exception.getMessage());
    }

    @Test
    @DisplayName("Erro ao atualizar usuário")
    void erroAoAtualizarUsuario() {
        AtualizarUsuarioRequest request = new AtualizarUsuarioRequest();
        request.setTipoUsuario(new TipoUsuarioDTO("Admin"));
        UsuarioEntity usuarioEntity = new UsuarioEntity();
        TipoUsuarioEntity tipoUsuario = new TipoUsuarioEntity();

        when(usuarioRepository.findById(anyInt())).thenReturn(Optional.of(usuarioEntity));
        when(tipoUsuarioService.buscarTipoUsuarioPorNome(anyString())).thenReturn(tipoUsuario);
        when(usuarioRepository.save(any(UsuarioEntity.class))).thenThrow(new ErroInternoException("Erro ao atualizar usuário"));

        Exception exception = assertThrows(ErroInternoException.class, () -> {
            usuarioService.atualizarUsuario(1, request);
        });

        assertTrue(exception.getMessage().contains("Erro ao atualizar usuário"));
    }

    @Test
    @DisplayName("Alterar senha com sucesso")
    void alterarSenhaComSucesso() {
        AlterarSenhaRequest request = new AlterarSenhaRequest();
        request.setSenhaAntiga("senhaAntiga");
        request.setNovaSenha("novaSenha");
        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity.setSenha("senhaAntiga");

        when(usuarioRepository.findById(anyInt())).thenReturn(Optional.of(usuarioEntity));
        when(usuarioRepository.save(any(UsuarioEntity.class))).thenReturn(usuarioEntity);

        AtualizarSenhaResponse result = usuarioService.alterarSenha(1, request);

        assertEquals(ConstantUtils.SENHA_ALTERADA, result.getMensagem());
    }

    @Test
    @DisplayName("Alterar senha com ID inexistente")
    void alterarSenhaComIdInexistente() {
        AlterarSenhaRequest request = new AlterarSenhaRequest();

        when(usuarioRepository.findById(anyInt())).thenReturn(Optional.empty());

        Exception exception = assertThrows(UsuarioNotExistsException.class, () -> {
            usuarioService.alterarSenha(1, request);
        });

        assertEquals(ConstantUtils.USUARIO_NAO_ENCONTRADO, exception.getMessage());
    }

    @Test
    @DisplayName("Alterar senha com senha antiga incorreta")
    void alterarSenhaComSenhaAntigaIncorreta() {
        AlterarSenhaRequest request = new AlterarSenhaRequest();
        request.setSenhaAntiga("senhaErro");
        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity.setSenha("senhaAntiga");

        when(usuarioRepository.findById(anyInt())).thenReturn(Optional.of(usuarioEntity));

        Exception exception = assertThrows(SenhaIncorretaException.class, () -> {
            usuarioService.alterarSenha(1, request);
        });

        assertEquals(ConstantUtils.ERRO_SENHA, exception.getMessage());
    }

    @Test
    @DisplayName("Erro ao alterar senha")
    void erroAoAlterarSenha() {
        AlterarSenhaRequest request = new AlterarSenhaRequest();
        request.setSenhaAntiga("senhaAntiga");
        request.setNovaSenha("novaSenha");
        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity.setSenha("senhaAntiga");

        when(usuarioRepository.findById(anyInt())).thenReturn(Optional.of(usuarioEntity));
        when(usuarioRepository.save(any(UsuarioEntity.class))).thenThrow(new ErroInternoException("Erro ao alterar senha"));

        Exception exception = assertThrows(ErroInternoException.class, () -> {
            usuarioService.alterarSenha(1, request);
        });

        assertTrue(exception.getMessage().contains("Erro ao alterar senha"));
    }

    @Test
    @DisplayName("Validar login com sucesso")
    void validarLoginComSucesso() {
        ValidaLoginUsuarioRequest request = new ValidaLoginUsuarioRequest();
        request.setLogin("usuario");
        request.setSenha("senha");
        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity.setSenha("senha");

        when(usuarioRepository.findByLogin(anyString())).thenReturn(Optional.of(usuarioEntity));

        ValidaLoginUsuarioResponse result = usuarioService.validarLogin(request);

        assertEquals(ConstantUtils.LOGIN_VALIDADO, result.getMensagem());
    }

    @Test
    @DisplayName("Validar login com usuário inexistente")
    void validarLoginComUsuarioInexistente() {
        ValidaLoginUsuarioRequest request = new ValidaLoginUsuarioRequest();
        request.setLogin("usuarioErro");

        when(usuarioRepository.findByLogin(anyString())).thenReturn(Optional.empty());

        Exception exception = assertThrows(UsuarioNotExistsException.class, () -> {
            usuarioService.validarLogin(request);
        });

        assertEquals(ConstantUtils.ERRO_LOGIN, exception.getMessage());
    }

    @Test
    @DisplayName("Validar login com senha incorreta")
    void validarLoginComSenhaIncorreta() {
        ValidaLoginUsuarioRequest request = new ValidaLoginUsuarioRequest();
        request.setLogin("usuario");
        request.setSenha("senhaErro");
        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity.setSenha("senhaOk");

        when(usuarioRepository.findByLogin(anyString())).thenReturn(Optional.of(usuarioEntity));

        Exception exception = assertThrows(SenhaIncorretaException.class, () -> {
            usuarioService.validarLogin(request);
        });

        assertEquals(ConstantUtils.ERRO_SENHA, exception.getMessage());
    }



    @Test
    @DisplayName("Deletar usuário com sucesso")
    void deletarUsuarioComSucesso() {
        UsuarioEntity usuarioEntity = new UsuarioEntity();

        when(usuarioRepository.findById(anyInt())).thenReturn(Optional.of(usuarioEntity));

        MensagemResponse result = usuarioService.deletarUsuario(1);

        assertEquals(ConstantUtils.USUARIO_DELETADO, result.getMensagem());
    }

    @Test
    @DisplayName("Deletar usuário com ID inexistente")
    void deletarUsuarioComIdInexistente() {
        when(usuarioRepository.findById(anyInt())).thenReturn(Optional.empty());

        Exception exception = assertThrows(UsuarioNotExistsException.class, () -> {
            usuarioService.deletarUsuario(1);
        });

        assertEquals(ConstantUtils.USUARIO_NAO_ENCONTRADO, exception.getMessage());
    }

    @Test
    @DisplayName("Erro ao deletar usuário")
    void erroAoDeletarUsuario() {
        UsuarioEntity usuarioEntity = new UsuarioEntity();

        when(usuarioRepository.findById(anyInt())).thenReturn(Optional.of(usuarioEntity));
        doThrow(new ErroInternoException("Erro ao deletar usuário")).when(usuarioRepository).delete(any(UsuarioEntity.class));

        Exception exception = assertThrows(ErroInternoException.class, () -> {
            usuarioService.deletarUsuario(1);
        });

        assertTrue(exception.getMessage().contains("Erro ao deletar usuário"));
    }

    @Test
    @DisplayName("Listar todos os usuários com sucesso")
    void listarTodosUsuariosComSucesso() {

        TipoUsuarioEntity tipoUsuario = new TipoUsuarioEntity();
        List<UsuarioEntity> usuarios = List.of(new UsuarioEntity(), new UsuarioEntity());
        usuarios.stream().forEach(f -> f.setTipoUsuario(tipoUsuario));

        when(usuarioRepository.findAll()).thenReturn(usuarios);

        List<UsuarioResponse> result = usuarioService.listarUsuarios();

        assertEquals(2, result.size());
    }

    @Test
    @DisplayName("Listar usuários quando não há usuários")
    void listarUsuariosQuandoNaoHaUsuarios() {
        when(usuarioRepository.findAll()).thenReturn(Collections.emptyList());

        List<UsuarioResponse> result = usuarioService.listarUsuarios();

        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Erro ao listar usuários")
    void erroAoListarUsuarios() {
        when(usuarioRepository.findAll()).thenThrow(new ErroInternoException("Erro ao listar usuários"));

        Exception exception = assertThrows(ErroInternoException.class, () -> {
            usuarioService.listarUsuarios();
        });

        assertTrue(exception.getMessage().contains("Erro ao listar usuários"));
    }
}
