package br.com.fiap.restaurantes.gerenciamento.integration;

import br.com.fiap.restaurantes.gerenciamento.application.dto.request.RestauranteRequest;
import br.com.fiap.restaurantes.gerenciamento.application.service.impl.TipoUsuarioServiceImpl;
import br.com.fiap.restaurantes.gerenciamento.domain.RestauranteEntity;
import br.com.fiap.restaurantes.gerenciamento.domain.TipoUsuarioEntity;
import br.com.fiap.restaurantes.gerenciamento.domain.UsuarioEntity;
import br.com.fiap.restaurantes.gerenciamento.infra.repository.RestauranteRepository;
import br.com.fiap.restaurantes.gerenciamento.infra.repository.TipoUsuarioRepository;
import br.com.fiap.restaurantes.gerenciamento.infra.repository.UsuarioRepository;
import br.com.fiap.restaurantes.gerenciamento.utils.ConstantUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class RestauranteIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    TipoUsuarioRepository tipoUsuarioRepository;

    @Autowired
    TipoUsuarioServiceImpl tipoUsuarioService;

    private UsuarioEntity donoRestaurante;
    private TipoUsuarioEntity tipoUsuario;


    @BeforeEach
    void setup() {
        restauranteRepository.deleteAll();
        usuarioRepository.deleteAll();

        tipoUsuario = tipoUsuarioService.buscarTipoUsuarioPorNome("Dono de Restaurante");


        // Inicializa o dono do restaurante
        donoRestaurante = new UsuarioEntity(); // Inicializa o atributo da classe
        donoRestaurante.setNome("João");
        donoRestaurante.setLogin("joao123");
        donoRestaurante.setEmail("joao@email.com");
        donoRestaurante.setSenha("123");
        donoRestaurante.setEndereco("Rua das Flores, 123");
        donoRestaurante.setTipoUsuario(tipoUsuario);
        donoRestaurante = usuarioRepository.save(donoRestaurante); // Salva no banco

    }

    @Test
    @DisplayName("Cadastrar restaurante com sucesso")
    void cadastrarRestauranteComSucesso() throws Exception {

        // Request de cadastro do restaurante
        RestauranteRequest request = new RestauranteRequest();
        request.setNomeRestaurante("Restaurante Saboroso");
        request.setEnderecoRestaurante("Rua das Flores, 123");
        request.setTipoCozinha("Italiana");
        request.setHorarioFuncionamento("08:00 - 18:00");
        request.setIdDonoRestaurante(donoRestaurante.getId());

        // Executa a requisição POST
        mockMvc.perform(post("/restaurantes/cadastrar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.mensagem").value(ConstantUtils.RESTAURANTE_CADASTRADO));

        // Verifica se o restaurante foi cadastrado no banco
        List<RestauranteEntity> restauranteSalvo = restauranteRepository.findAll();
        assertEquals(1, restauranteSalvo.size());
        RestauranteEntity restaurante = restauranteSalvo.getFirst();

        // Validações do restaurante salvo
        assertEquals("Restaurante Saboroso", restaurante.getNomeRestaurante());
        assertEquals("Rua das Flores, 123", restaurante.getEnderecoRestaurante());
        assertEquals("Italiana", restaurante.getTipoCozinha());
        assertEquals(donoRestaurante.getId(), restaurante.getDonoRestaurante().getId());
    }

    @Test
    @DisplayName("Tentar cadastrar restaurante com dono inexistente")
    void cadastrarRestauranteDonoInexistente() throws Exception {
        // Request de cadastro com ID de dono inexistente
        RestauranteRequest request = new RestauranteRequest();
        request.setNomeRestaurante("Restaurante Saboroso");
        request.setEnderecoRestaurante("Rua das Flores, 123");
        request.setTipoCozinha("Italiana");
        request.setHorarioFuncionamento("08:00 - 18:00");
        request.setIdDonoRestaurante(999);

        mockMvc.perform(post("/restaurantes/cadastrar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(request)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.mensagem").value(ConstantUtils.DONO_NAO_ENCONTRADO));
    }

    @Test
    @DisplayName("Tentar cadastrar restaurante já existente")
    void cadastrarRestauranteJaExistente() throws Exception {
        RestauranteEntity restauranteExistente = new RestauranteEntity();
        restauranteExistente.setNomeRestaurante("Restaurante Saboroso");
        restauranteExistente.setEnderecoRestaurante("Rua das Flores, 123");
        restauranteExistente.setTipoCozinha("Italiana");
        restauranteExistente.setHorarioFuncionamento("08:00 - 18:00");
        restauranteExistente.setDonoRestaurante(donoRestaurante);

        restauranteRepository.save(restauranteExistente);

        // Request de cadastro com o mesmo nome
        RestauranteRequest request = new RestauranteRequest();
        request.setNomeRestaurante("Restaurante Saboroso");
        request.setEnderecoRestaurante("Rua das Orquídeas, 456");
        request.setTipoCozinha("Italiana");
        request.setHorarioFuncionamento("08:00 - 18:00");
        request.setIdDonoRestaurante(donoRestaurante.getId());

        // Executa a requisição POST e espera erro
        mockMvc.perform(post("/restaurantes/cadastrar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(request)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.mensagem").value(ConstantUtils.RESTAURANTE_EXISTENTE));
    }


    @Test
    @DisplayName("Atualizar restaurante com sucesso")
    void atualizarRestauranteComSucesso() throws Exception {
        RestauranteEntity restauranteExistente = new RestauranteEntity();
        restauranteExistente.setNomeRestaurante("Restaurante Original");
        restauranteExistente.setEnderecoRestaurante("Rua Principal, 123");
        restauranteExistente.setTipoCozinha("Brasileira");
        restauranteExistente.setHorarioFuncionamento("09:00 - 21:00");
        restauranteExistente.setDonoRestaurante(donoRestaurante);

        restauranteRepository.save(restauranteExistente);

        RestauranteRequest request = new RestauranteRequest();
        request.setNomeRestaurante("Restaurante Atualizado");
        request.setEnderecoRestaurante("Rua Nova, 456");
        request.setTipoCozinha("Italiana");
        request.setHorarioFuncionamento("10:00 - 22:00");
        request.setIdDonoRestaurante(donoRestaurante.getId());

        mockMvc.perform(put("/restaurantes/atualizar/{idRestaurante}", restauranteExistente.getIdRestaurante())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mensagem").value(ConstantUtils.RESTAURANTE_ATUALIZADO));

        RestauranteEntity restauranteAtualizado = restauranteRepository.findById(restauranteExistente.getIdRestaurante()).orElseThrow();
        assertEquals("Restaurante Atualizado", restauranteAtualizado.getNomeRestaurante());
        assertEquals("Rua Nova, 456", restauranteAtualizado.getEnderecoRestaurante());
        assertEquals("Italiana", restauranteAtualizado.getTipoCozinha());
    }

    @Test
    @DisplayName("Tentar atualizar restaurante inexistente")
    void atualizarRestauranteInexistente() throws Exception {
        RestauranteRequest request = new RestauranteRequest();
        request.setNomeRestaurante("Restaurante Novo");
        request.setEnderecoRestaurante("Rua Inexistente, 789");
        request.setTipoCozinha("Japonesa");
        request.setHorarioFuncionamento("12:00 - 20:00");
        request.setIdDonoRestaurante(donoRestaurante.getId());

        mockMvc.perform(put("/restaurantes/atualizar/{idRestaurante}", 999)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(request)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.mensagem").value(ConstantUtils.RESTAURANTE_NAO_ENCONTRADO));
    }

    @Test
    @DisplayName("Tentar atualizar restaurante com dono inexistente")
    void atualizarRestauranteDonoInexistente() throws Exception {
        RestauranteEntity restauranteExistente = new RestauranteEntity();
        restauranteExistente.setNomeRestaurante("Restaurante Original");
        restauranteExistente.setEnderecoRestaurante("Rua Principal, 123");
        restauranteExistente.setTipoCozinha("Brasileira");
        restauranteExistente.setHorarioFuncionamento("09:00 - 21:00");
        restauranteExistente.setDonoRestaurante(donoRestaurante);

        restauranteRepository.save(restauranteExistente);

        RestauranteRequest request = new RestauranteRequest();
        request.setNomeRestaurante("Restaurante Atualizado");
        request.setEnderecoRestaurante("Rua Nova, 456");
        request.setTipoCozinha("Italiana");
        request.setHorarioFuncionamento("10:00 - 22:00");
        request.setIdDonoRestaurante(999); // Dono inexistente

        mockMvc.perform(put("/restaurantes/atualizar/{idRestaurante}", restauranteExistente.getIdRestaurante())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(request)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.mensagem").value(ConstantUtils.DONO_NAO_ENCONTRADO));
    }


    @Test
    @DisplayName("Listar restaurantes com sucesso")
    void listarRestaurantesComSucesso() throws Exception {

        RestauranteEntity restaurante1 = new RestauranteEntity();
        restaurante1.setNomeRestaurante("Restaurante Saboroso");
        restaurante1.setEnderecoRestaurante("Rua das Flores, 123");
        restaurante1.setTipoCozinha("Italiana");
        restaurante1.setHorarioFuncionamento("08:00 - 18:00");
        restaurante1.setDonoRestaurante(donoRestaurante);
        restauranteRepository.save(restaurante1);

        RestauranteEntity restaurante2 = new RestauranteEntity();
        restaurante2.setNomeRestaurante("Restaurante Delícia");
        restaurante2.setEnderecoRestaurante("Avenida Central, 456");
        restaurante2.setTipoCozinha("Japonesa");
        restaurante2.setHorarioFuncionamento("10:00 - 22:00");
        restaurante2.setDonoRestaurante(donoRestaurante);
        restauranteRepository.save(restaurante2);

        mockMvc.perform(get("/restaurantes")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].nomeRestaurante").value("Restaurante Saboroso"))
                .andExpect(jsonPath("$[0].enderecoRestaurante").value("Rua das Flores, 123"))
                .andExpect(jsonPath("$[0].tipoCozinha").value("Italiana"))
                .andExpect(jsonPath("$[1].nomeRestaurante").value("Restaurante Delícia"))
                .andExpect(jsonPath("$[1].enderecoRestaurante").value("Avenida Central, 456"))
                .andExpect(jsonPath("$[1].tipoCozinha").value("Japonesa"));
    }

    @Test
    @DisplayName("Listar restaurantes com lista vazia")
    void listarRestaurantesListaVazia() throws Exception {

        mockMvc.perform(get("/restaurantes")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @DisplayName("Deletar restaurante com sucesso")
    void deletarRestauranteComSucesso() throws Exception {

        RestauranteEntity restaurante = new RestauranteEntity();
        restaurante.setNomeRestaurante("Restaurante Saboroso");
        restaurante.setEnderecoRestaurante("Rua das Flores, 123");
        restaurante.setTipoCozinha("Italiana");
        restaurante.setHorarioFuncionamento("08:00 - 18:00");
        restaurante.setDonoRestaurante(donoRestaurante);
        restauranteRepository.save(restaurante);


        mockMvc.perform(delete("/restaurantes/deletar/" + restaurante.getIdRestaurante())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mensagem").value(ConstantUtils.RESTAURANTE_DELETADO));


        boolean existe = restauranteRepository.findById(restaurante.getIdRestaurante()).isPresent();
        assertFalse(existe);
    }

    @Test
    @DisplayName("Tentar deletar restaurante inexistente")
    void deletarRestauranteInexistente() throws Exception {
        mockMvc.perform(delete("/restaurantes/deletar/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.mensagem").value(ConstantUtils.RESTAURANTE_NAO_ENCONTRADO));
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
