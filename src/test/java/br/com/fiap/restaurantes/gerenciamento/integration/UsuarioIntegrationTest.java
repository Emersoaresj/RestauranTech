package br.com.fiap.restaurantes.gerenciamento.integration;

import br.com.fiap.restaurantes.gerenciamento.application.dto.TipoUsuarioDTO;
import br.com.fiap.restaurantes.gerenciamento.application.dto.UsuarioDTO;
import br.com.fiap.restaurantes.gerenciamento.application.dto.request.AlterarSenhaRequest;
import br.com.fiap.restaurantes.gerenciamento.application.dto.request.AtualizarUsuarioRequest;
import br.com.fiap.restaurantes.gerenciamento.application.dto.request.ValidaLoginUsuarioRequest;
import br.com.fiap.restaurantes.gerenciamento.application.service.impl.TipoUsuarioServiceImpl;
import br.com.fiap.restaurantes.gerenciamento.domain.TipoUsuarioEntity;
import br.com.fiap.restaurantes.gerenciamento.domain.UsuarioEntity;
import br.com.fiap.restaurantes.gerenciamento.infra.repository.RestauranteRepository;
import br.com.fiap.restaurantes.gerenciamento.infra.repository.TipoUsuarioRepository;
import br.com.fiap.restaurantes.gerenciamento.infra.repository.UsuarioRepository;
import br.com.fiap.restaurantes.gerenciamento.utils.ConstantUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class UsuarioIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TipoUsuarioRepository tipoUsuarioRepository;

    @Autowired
    TipoUsuarioServiceImpl tipoUsuarioService;

    @Autowired
    RestauranteRepository restauranteRepository;

    private UsuarioEntity usuario;
    private TipoUsuarioEntity tipoUsuario;


    @BeforeEach
    void setup() {
        restauranteRepository.deleteAll();
        usuarioRepository.deleteAll();

        tipoUsuario = tipoUsuarioService.buscarTipoUsuarioPorNome("Cliente");

        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setNome("Carlos");
        usuario.setLogin("carlos123");
        usuario.setEmail("carlos@email.com");
        usuario.setSenha("123456");
        usuario.setEndereco("Rua A, 123");
        usuario.setTipoUsuario(tipoUsuario);
        usuarioRepository.save(usuario);
    }

    @Test
    @DisplayName("Listar usuários com sucesso")
    void listarUsuariosComSucesso() throws Exception {
        mockMvc.perform(get("/usuarios")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].nome").value("Carlos"))
                .andExpect(jsonPath("$[0].login").value("carlos123"))
                .andExpect(jsonPath("$[0].email").value("carlos@email.com"))
                .andExpect(jsonPath("$[0].endereco").value("Rua A, 123"))
                .andExpect(jsonPath("$[0].tipoUsuario.nomeTipo").value("Cliente"));
    }

    @Test
    @DisplayName("Listar usuários - Nenhum usuário encontrado")
    void listarUsuariosNenhumEncontrado() throws Exception {
        usuarioRepository.deleteAll();

        mockMvc.perform(get("/usuarios")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    @DisplayName("Cadastrar usuário com sucesso")
    void cadastrarUsuarioComSucesso() throws Exception {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setNome("João");
        usuarioDTO.setLogin("joao123"); // Login diferente
        usuarioDTO.setEmail("joao@email.com");
        usuarioDTO.setSenha("123456");
        usuarioDTO.setEndereco("Rua B, 456");

        TipoUsuarioDTO tipoUsuarioDTO = new TipoUsuarioDTO();
        tipoUsuarioDTO.setNomeTipo("Cliente");
        usuarioDTO.setTipoUsuario(tipoUsuarioDTO);

        mockMvc.perform(post("/usuarios/cadastrar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(usuarioDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.mensagem").value(ConstantUtils.USUARIO_CADASTRADO));

        UsuarioEntity usuario = usuarioRepository.findByLogin("joao123").orElseThrow();
        Assertions.assertEquals("João", usuario.getNome());
        Assertions.assertEquals("Rua B, 456", usuario.getEndereco());
        Assertions.assertEquals("Cliente", usuario.getTipoUsuario().getNomeTipo());
    }


    @Test
    @DisplayName("Cadastrar usuário com login já existente")
    void cadastrarUsuarioComLoginExistente() throws Exception {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setNome("Carlos");
        usuarioDTO.setLogin("carlos123");
        usuarioDTO.setEmail("novo@email.com");
        usuarioDTO.setSenha("123456");
        usuarioDTO.setEndereco("Rua C, 789");

        TipoUsuarioDTO tipoUsuarioDTO = new TipoUsuarioDTO();
        tipoUsuarioDTO.setNomeTipo("Cliente");
        usuarioDTO.setTipoUsuario(tipoUsuarioDTO);

        mockMvc.perform(post("/usuarios/cadastrar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(usuarioDTO)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.mensagem").value(ConstantUtils.LOGIN_CADASTRADO));
    }


    @Test
    @DisplayName("Cadastrar usuário com tipo de usuário inválido")
    void cadastrarUsuarioComTipoUsuarioInvalido() throws Exception {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setNome("Maria");
        usuarioDTO.setLogin("maria123");
        usuarioDTO.setEmail("maria@email.com");
        usuarioDTO.setSenha("123456");
        usuarioDTO.setEndereco("Rua C, 789");

        // Tipo de usuário inválido
        TipoUsuarioDTO tipoUsuarioDTO = new TipoUsuarioDTO();
        tipoUsuarioDTO.setNomeTipo("Inexistente");
        usuarioDTO.setTipoUsuario(tipoUsuarioDTO);

        mockMvc.perform(post("/usuarios/cadastrar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(usuarioDTO)))
                .andExpect(status().isBadRequest()) // 400, por erro de validação
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors[0]").value("O tipo de usuário deve ser 'Cliente' ou 'Dono de Restaurante'"));
    }

    @Test
    @DisplayName("Atualizar usuário com sucesso")
    void atualizarUsuarioComSucesso() throws Exception {

        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setNome("Carlos");
        usuario.setLogin("carlos123");
        usuario.setEmail("carlos@email.com");
        usuario.setSenha("123456");
        usuario.setEndereco("Rua A, 123");
        usuario.setTipoUsuario(tipoUsuario);
        usuarioRepository.save(usuario);

        AtualizarUsuarioRequest request = new AtualizarUsuarioRequest();
        request.setNome("Carlos Atualizado");
        request.setEmail("carlos.atualizado@email.com");
        request.setLogin("carlos123");
        request.setEndereco("Rua B, 456");
        TipoUsuarioDTO tipoUsuarioDTO = new TipoUsuarioDTO();
        tipoUsuarioDTO.setNomeTipo("Cliente");
        request.setTipoUsuario(tipoUsuarioDTO);

        mockMvc.perform(put("/usuarios/{id}", usuario.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mensagem").value(ConstantUtils.USUARIO_ATUALIZADO));

        UsuarioEntity usuarioAtualizado = usuarioRepository.findById(usuario.getId()).orElseThrow();
        Assertions.assertEquals("Carlos Atualizado", usuarioAtualizado.getNome());
        Assertions.assertEquals("carlos.atualizado@email.com", usuarioAtualizado.getEmail());
        Assertions.assertEquals("Rua B, 456", usuarioAtualizado.getEndereco());
        Assertions.assertEquals("Cliente", usuarioAtualizado.getTipoUsuario().getNomeTipo());
    }

    @Test
    @DisplayName("Tentar atualizar um usuário inexistente")
    void atualizarUsuarioInexistente() throws Exception {
        AtualizarUsuarioRequest request = new AtualizarUsuarioRequest();
        request.setNome("Usuário Inexistente");
        request.setEmail("naoexiste@email.com");
        request.setLogin("login123");
        request.setEndereco("Rua C, 789");
        TipoUsuarioDTO tipoUsuarioDTO = new TipoUsuarioDTO();
        tipoUsuarioDTO.setNomeTipo("Cliente");
        request.setTipoUsuario(tipoUsuarioDTO);

        mockMvc.perform(put("/usuarios/{id}", 9999) // ID inexistente
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(request)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.mensagem").value(ConstantUtils.USUARIO_NAO_ENCONTRADO));
    }

    @Test
    @DisplayName("Alterar senha de usuário com sucesso")
    void alterarSenhaComSucesso() throws Exception {
        TipoUsuarioEntity tipoUsuario = tipoUsuarioService.buscarTipoUsuarioPorNome("Cliente");

        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setNome("Carlos");
        usuario.setLogin("carlos123");
        usuario.setEmail("carlos@email.com");
        usuario.setSenha("senhaAntiga123");
        usuario.setEndereco("Rua A, 123");
        usuario.setTipoUsuario(tipoUsuario);
        usuarioRepository.save(usuario);

        AlterarSenhaRequest alterarSenhaRequest = new AlterarSenhaRequest();
        alterarSenhaRequest.setSenhaAntiga("senhaAntiga123");
        alterarSenhaRequest.setNovaSenha("novaSenha123");

        mockMvc.perform(post("/usuarios/alterar-senha/{id}", usuario.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(alterarSenhaRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mensagem").value(ConstantUtils.SENHA_ALTERADA));

        UsuarioEntity usuarioAtualizado = usuarioRepository.findById(usuario.getId()).orElseThrow();
        Assertions.assertEquals("novaSenha123", usuarioAtualizado.getSenha());
    }

    @Test
    @DisplayName("Tentar alterar senha de um usuário inexistente")
    void alterarSenhaUsuarioInexistente() throws Exception {
        AlterarSenhaRequest alterarSenhaRequest = new AlterarSenhaRequest();
        alterarSenhaRequest.setSenhaAntiga("senhaAntiga123");
        alterarSenhaRequest.setNovaSenha("novaSenha123");

        mockMvc.perform(post("/usuarios/alterar-senha/{id}", 9999) // ID inexistente
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(alterarSenhaRequest)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.mensagem").value(ConstantUtils.USUARIO_NAO_ENCONTRADO));
    }

    @Test
    @DisplayName("Validar login com sucesso")
    void validarLoginComSucesso() throws Exception {
        TipoUsuarioEntity tipoUsuario = tipoUsuarioService.buscarTipoUsuarioPorNome("Cliente");

        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setNome("João");
        usuario.setLogin("joao123");
        usuario.setEmail("joao@email.com");
        usuario.setSenha("senhaCorreta");
        usuario.setEndereco("Rua B, 456");
        usuario.setTipoUsuario(tipoUsuario);
        usuarioRepository.save(usuario);

        // Criando o request de validação de login
        ValidaLoginUsuarioRequest validaLoginUsuarioRequest = new ValidaLoginUsuarioRequest();
        validaLoginUsuarioRequest.setLogin("joao123");
        validaLoginUsuarioRequest.setSenha("senhaCorreta");

        mockMvc.perform(post("/usuarios/validar-login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(validaLoginUsuarioRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mensagem").value(ConstantUtils.LOGIN_VALIDADO))
                .andExpect(jsonPath("$.login").value("joao123"));
    }

    @Test
    @DisplayName("Tentar validar login com login inexistente")
    void validarLoginComLoginInexistente() throws Exception {
        ValidaLoginUsuarioRequest validaLoginUsuarioRequest = new ValidaLoginUsuarioRequest();
        validaLoginUsuarioRequest.setLogin("usuarioInexistente");
        validaLoginUsuarioRequest.setSenha("senhaQualquer");

        mockMvc.perform(post("/usuarios/validar-login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(validaLoginUsuarioRequest)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.mensagem").value(ConstantUtils.ERRO_LOGIN));
    }

    @Test
    @DisplayName("Tentar validar login com senha incorreta")
    void validarLoginComSenhaIncorreta() throws Exception {
        TipoUsuarioEntity tipoUsuario = tipoUsuarioService.buscarTipoUsuarioPorNome("Cliente");

        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setNome("João");
        usuario.setLogin("joao123");
        usuario.setEmail("joao@email.com");
        usuario.setSenha("senhaCorreta");
        usuario.setEndereco("Rua B, 456");
        usuario.setTipoUsuario(tipoUsuario);
        usuarioRepository.save(usuario);

        // Criando o request com a senha incorreta
        ValidaLoginUsuarioRequest validaLoginUsuarioRequest = new ValidaLoginUsuarioRequest();
        validaLoginUsuarioRequest.setLogin("joao123");
        validaLoginUsuarioRequest.setSenha("senhaErrada");

        mockMvc.perform(post("/usuarios/validar-login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(validaLoginUsuarioRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.mensagem").value(ConstantUtils.ERRO_SENHA));
    }

    @Test
    @DisplayName("Deletar usuário com sucesso")
    void deletarUsuarioComSucesso() throws Exception {
        TipoUsuarioEntity tipoUsuario = tipoUsuarioService.buscarTipoUsuarioPorNome("Cliente");

        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setNome("Maria");
        usuario.setLogin("maria123");
        usuario.setEmail("maria@email.com");
        usuario.setSenha("senhaMaria");
        usuario.setEndereco("Rua C, 789");
        usuario.setTipoUsuario(tipoUsuario);
        usuarioRepository.save(usuario);

        mockMvc.perform(delete("/usuarios/{id}", usuario.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mensagem").value(ConstantUtils.USUARIO_DELETADO));

        Assertions.assertFalse(usuarioRepository.findById(usuario.getId()).isPresent());
    }

    @Test
    @DisplayName("Tentar deletar usuário inexistente")
    void deletarUsuarioInexistente() throws Exception {
        mockMvc.perform(delete("/usuarios/{id}", 999)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.mensagem").value(ConstantUtils.USUARIO_NAO_ENCONTRADO));
    }




    // Método utilitário para converter um objeto para JSON
    private static String asJsonString(final Object obj) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.findAndRegisterModules();
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
