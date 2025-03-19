package br.com.fiap.restaurantes.gerenciamento.service;

import br.com.fiap.restaurantes.gerenciamento.application.service.impl.TipoUsuarioServiceImpl;
import br.com.fiap.restaurantes.gerenciamento.domain.TipoUsuarioEntity;
import br.com.fiap.restaurantes.gerenciamento.infra.exception.tipoUsuario.TipoUsuarioNotExistException;
import br.com.fiap.restaurantes.gerenciamento.infra.repository.TipoUsuarioRepository;
import br.com.fiap.restaurantes.gerenciamento.utils.ConstantUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
public class TipoUsuarioServiceImplTest {

    @InjectMocks
    private TipoUsuarioServiceImpl tipoUsuarioService;

    @Mock
    private TipoUsuarioRepository tipoUsuarioRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Buscar tipo de usuário por nome com sucesso")
    void buscarTipoUsuarioPorNomeComSucesso() {
        TipoUsuarioEntity tipoUsuario = new TipoUsuarioEntity();
        tipoUsuario.setNomeTipo("Admin");
        when(tipoUsuarioRepository.findByNomeTipo(anyString())).thenReturn(Optional.of(tipoUsuario));

        TipoUsuarioEntity result = tipoUsuarioService.buscarTipoUsuarioPorNome(tipoUsuario.getNomeTipo());

        assertEquals(tipoUsuario, result);
    }

    @Test
    @DisplayName("Buscar tipo de usuário por nome inexistente")
    void buscarTipoUsuarioPorNomeInexistente() {
        when(tipoUsuarioRepository.findByNomeTipo(anyString())).thenReturn(Optional.empty());

        Exception exception = assertThrows(TipoUsuarioNotExistException.class, () -> {
            tipoUsuarioService.buscarTipoUsuarioPorNome("NaoExiste");
        });

        assertEquals(ConstantUtils.TIPO_USUARIO_NAO_ENCONTRADO, exception.getMessage());
    }
}
