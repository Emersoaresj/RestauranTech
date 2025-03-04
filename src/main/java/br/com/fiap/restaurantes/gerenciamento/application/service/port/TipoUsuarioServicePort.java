package br.com.fiap.restaurantes.gerenciamento.application.service.port;

import br.com.fiap.restaurantes.gerenciamento.application.dto.TipoUsuarioDTO;
import br.com.fiap.restaurantes.gerenciamento.application.dto.request.TipoUsuarioRequest;
import br.com.fiap.restaurantes.gerenciamento.application.dto.response.MensagemResponse;
import br.com.fiap.restaurantes.gerenciamento.application.dto.response.TipoUsuarioResponse;
import br.com.fiap.restaurantes.gerenciamento.application.dto.response.UsuarioResponse;
import br.com.fiap.restaurantes.gerenciamento.domain.model.TipoUsuarioEntity;

import java.util.List;

public interface TipoUsuarioServicePort {

    MensagemResponse cadastrarTipoUsuario(TipoUsuarioRequest dto);

    MensagemResponse atualizarTipoUsuario(Integer id, TipoUsuarioRequest dto);

    List<TipoUsuarioResponse> listarTipoUsuario();

    MensagemResponse deletarTipoUsuario(Integer id);

    UsuarioResponse associarTipoUsuarioAoUsuario(String nomeTipo, Integer idUsuario);

    TipoUsuarioEntity buscarTipoUsuarioPorNome(String nomeTipo);
}
