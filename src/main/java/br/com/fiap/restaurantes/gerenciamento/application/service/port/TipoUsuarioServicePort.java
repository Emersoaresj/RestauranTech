package br.com.fiap.restaurantes.gerenciamento.application.service.port;

import br.com.fiap.restaurantes.gerenciamento.domain.TipoUsuarioEntity;

public interface TipoUsuarioServicePort {

    TipoUsuarioEntity buscarTipoUsuarioPorNome(String nomeTipo);
}
