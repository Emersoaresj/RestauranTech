package br.com.fiap.restaurantes.gerenciamento.integration;

import br.com.fiap.restaurantes.gerenciamento.application.dto.request.CardapioRequest;
import br.com.fiap.restaurantes.gerenciamento.domain.CardapioEntity;
import br.com.fiap.restaurantes.gerenciamento.infra.exception.cardapio.CardapioNotExistsException;
import br.com.fiap.restaurantes.gerenciamento.infra.repository.CardapioRepository;
import br.com.fiap.restaurantes.gerenciamento.utils.ConstantUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CardapioIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CardapioRepository cardapioRepository;

    @BeforeEach
    public void setUp() {
        cardapioRepository.deleteAll();
    }

    @Test
    @DisplayName("Cadastrar cardápio com sucesso")
    void cadastrarCardapioComSucesso() throws Exception {
        CardapioRequest request = new CardapioRequest();
        request.setNome("Pizza");
        request.setDescricao("Uma deliciosa pizza italiana");
        request.setPreco(25.90);
        request.setDisponibilidadeConsumo("LOCAL");

        // Realiza a chamada ao endpoint
        mockMvc.perform(post("/cardapio/cadastrar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.mensagem").value(ConstantUtils.CARDAPIO_CADASTRADO));

        // Verifica se o cardápio foi salvo no banco de dados
        List<CardapioEntity> cardapios = cardapioRepository.findAll();
        assertEquals(1, cardapios.size());
        assertEquals("Pizza", cardapios.getFirst().getNome());
        assertEquals("Uma deliciosa pizza italiana", cardapios.getFirst().getDescricao());
    }

    @Test
    @DisplayName("Cadastrar cardápio com nome existente")
    void cadastrarCardapioComNomeExistente() throws Exception {
        // Insere um cardápio no banco de dados
        CardapioEntity existingCardapio = new CardapioEntity();
        existingCardapio.setNome("Pizza");
        existingCardapio.setDescricao("Uma pizza já existente");
        existingCardapio.setPreco(25.90);
        existingCardapio.setDisponibilidadeConsumo("LOCAL");
        cardapioRepository.save(existingCardapio);

        // Configuração do objeto de requisição
        CardapioRequest request = new CardapioRequest();
        request.setNome("Pizza");
        request.setDescricao("Uma deliciosa pizza italiana");
        request.setPreco(25.90);
        request.setDisponibilidadeConsumo("LOCAL");

        // Realiza a chamada ao endpoint e espera um erro de conflito
        mockMvc.perform(post("/cardapio/cadastrar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(request)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.mensagem").value(ConstantUtils.CARDAPIO_EXISTENTE));

        // Verifica que não houve duplicação no banco de dados
        List<CardapioEntity> cardapios = cardapioRepository.findAll();
        assertEquals(1, cardapios.size());
    }

    @Test
    @DisplayName("Atualizar cardápio com sucesso")
    void atualizarCardapioComSucesso() throws Exception {
        CardapioEntity cardapioExistente = new CardapioEntity();
        cardapioExistente.setNome("Pizza");
        cardapioExistente.setDescricao("Uma deliciosa pizza italiana");
        cardapioExistente.setPreco(25.90);
        cardapioExistente.setDisponibilidadeConsumo("LOCAL");
        cardapioRepository.save(cardapioExistente);

        CardapioRequest request = new CardapioRequest();
        request.setNome("Pizza de Frango");
        request.setDescricao("Uma deliciosa pizza de Frango");
        request.setPreco(30.90);
        request.setDisponibilidadeConsumo("LOCAL");

        mockMvc.perform(put("/cardapio/atualizar/{idCardapio}", cardapioExistente.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mensagem").value(ConstantUtils.CARDAPIO_ATUALIZADO));

        CardapioEntity cardapioAtualizado = cardapioRepository.findById(cardapioExistente.getId())
                .orElseThrow(() -> new CardapioNotExistsException(ConstantUtils.CARDAPIO_NAO_ENCONTRADO));

        assertEquals("Pizza de Frango", cardapioAtualizado.getNome());
        assertEquals("Uma deliciosa pizza de Frango", cardapioAtualizado.getDescricao());
        assertEquals(30.90, cardapioAtualizado.getPreco());
        assertEquals("LOCAL", cardapioAtualizado.getDisponibilidadeConsumo());
    }

    @Test
    @DisplayName("Erro ao atualizar cardápio com ID inexistente")
    void erroAoAtualizarCardapioIdInexistente() throws Exception {

        CardapioRequest request = new CardapioRequest();
        request.setNome("Pizza de Calabresa");
        request.setDescricao("Uma deliciosa pizza de calabresa");
        request.setPreco(29.90);
        request.setDisponibilidadeConsumo("LOCAL");

        Integer idInexistente = 999;

        mockMvc.perform(put("/cardapio/atualizar/{idCardapio}", idInexistente)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(request)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.mensagem").value(ConstantUtils.CARDAPIO_NAO_ENCONTRADO));

        // Certifica-se de que nenhum cardápio foi salvo no banco
        List<CardapioEntity> cardapios = cardapioRepository.findAll();
        assertEquals(0, cardapios.size());
    }

    @Test
    @DisplayName("Listar cardápios com sucesso")
    void listarCardapiosComSucesso() throws Exception {

        CardapioEntity cardapio1 = new CardapioEntity();
        cardapio1.setNome("Pizza");
        cardapio1.setDescricao("Uma deliciosa pizza italiana");
        cardapio1.setPreco(25.90);
        cardapio1.setDisponibilidadeConsumo("LOCAL");
        cardapioRepository.save(cardapio1);

        CardapioEntity cardapio2 = new CardapioEntity();
        cardapio2.setNome("Hambúrguer");
        cardapio2.setDescricao("Um delicioso hambúrguer artesanal");
        cardapio2.setPreco(15.50);
        cardapio2.setDisponibilidadeConsumo("LIVRE");
        cardapioRepository.save(cardapio2);

        // Realiza a chamada ao endpoint GET /cardapio
        mockMvc.perform(get("/cardapio")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].nome").value("Pizza"))
                .andExpect(jsonPath("$[0].descricao").value("Uma deliciosa pizza italiana"))
                .andExpect(jsonPath("$[1].nome").value("Hambúrguer"))
                .andExpect(jsonPath("$[1].descricao").value("Um delicioso hambúrguer artesanal"));
    }

    @Test
    @DisplayName("Listar cardápios com banco vazio")
    void listarCardapiosBancoVazio() throws Exception {
        cardapioRepository.deleteAll();


        mockMvc.perform(get("/cardapio")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    @DisplayName("Deletar cardápio com sucesso")
    void deletarCardapioComSucesso() throws Exception {

        CardapioEntity cardapioExistente = new CardapioEntity();
        cardapioExistente.setNome("Pizza");
        cardapioExistente.setDescricao("Uma deliciosa pizza italiana");
        cardapioExistente.setPreco(25.90);
        cardapioExistente.setDisponibilidadeConsumo("LOCAL");
        cardapioRepository.save(cardapioExistente);


        mockMvc.perform(delete("/cardapio/deletar/{idCardapio}", cardapioExistente.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mensagem").value(ConstantUtils.CARDAPIO_DELETADO));

        // Verifica se o cardápio foi deletado do banco
        boolean cardapioExiste = cardapioRepository.existsById(cardapioExistente.getId());
        assertFalse(cardapioExiste, "O cardápio deveria ter sido deletado, mas ainda existe no banco de dados.");
    }

    @Test
    @DisplayName("Tentar deletar cardápio inexistente")
    void deletarCardapioInexistente() throws Exception {
        Integer idInexistente = 999;

        mockMvc.perform(delete("/cardapio/deletar/{idCardapio}", idInexistente)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.mensagem").value(ConstantUtils.CARDAPIO_NAO_ENCONTRADO));
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
