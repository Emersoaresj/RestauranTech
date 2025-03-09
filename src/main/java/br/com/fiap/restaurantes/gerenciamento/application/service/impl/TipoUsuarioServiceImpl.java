package br.com.fiap.restaurantes.gerenciamento.application.service.impl;

import br.com.fiap.restaurantes.gerenciamento.application.service.port.TipoUsuarioServicePort;
import br.com.fiap.restaurantes.gerenciamento.domain.TipoUsuarioEntity;
import br.com.fiap.restaurantes.gerenciamento.infra.exception.tipoUsuario.TipoUsuarioNotExistException;
import br.com.fiap.restaurantes.gerenciamento.infra.repository.TipoUsuarioRepository;
import br.com.fiap.restaurantes.gerenciamento.utils.ConstantUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TipoUsuarioServiceImpl implements TipoUsuarioServicePort {

    @Autowired
    TipoUsuarioRepository tipoUsuarioRepository;

    @Override
    public TipoUsuarioEntity buscarTipoUsuarioPorNome(String nomeTipo) {
        return tipoUsuarioRepository.findByNomeTipo(nomeTipo)
                .orElseThrow(() -> new TipoUsuarioNotExistException(ConstantUtils.TIPO_USUARIO_NAO_ENCONTRADO));
    }
}
