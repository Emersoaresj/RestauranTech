package br.com.fiap.restaurantes.gerenciamento.application.service.impl;

import br.com.fiap.restaurantes.gerenciamento.application.dto.TipoUsuarioDTO;
import br.com.fiap.restaurantes.gerenciamento.application.dto.request.TipoUsuarioRequest;
import br.com.fiap.restaurantes.gerenciamento.application.dto.response.MensagemResponse;
import br.com.fiap.restaurantes.gerenciamento.application.dto.response.TipoUsuarioResponse;
import br.com.fiap.restaurantes.gerenciamento.application.dto.response.UsuarioResponse;
import br.com.fiap.restaurantes.gerenciamento.application.service.port.TipoUsuarioServicePort;
import br.com.fiap.restaurantes.gerenciamento.domain.model.TipoUsuarioEntity;
import br.com.fiap.restaurantes.gerenciamento.domain.model.UsuarioEntity;
import br.com.fiap.restaurantes.gerenciamento.infra.exception.ErroInternoException;
import br.com.fiap.restaurantes.gerenciamento.infra.exception.TipoUsuarioExistException;
import br.com.fiap.restaurantes.gerenciamento.infra.exception.TipoUsuarioNotExistException;
import br.com.fiap.restaurantes.gerenciamento.infra.exception.UsuarioNaoEncontradoException;
import br.com.fiap.restaurantes.gerenciamento.infra.mapper.TipoUsuarioMapper;
import br.com.fiap.restaurantes.gerenciamento.infra.mapper.UsuarioMapper;
import br.com.fiap.restaurantes.gerenciamento.infra.repository.TipoUsuarioRepository;
import br.com.fiap.restaurantes.gerenciamento.infra.repository.UsuarioRepository;
import br.com.fiap.restaurantes.gerenciamento.utils.ConstantUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class TipoUsuarioServiceImpl implements TipoUsuarioServicePort {

    @Autowired
    TipoUsuarioRepository tipoUsuarioRepository;

    @Autowired
    UsuarioRepository usuarioRepository;


    @Override
    public MensagemResponse cadastrarTipoUsuario(TipoUsuarioRequest dto) {

        if (!ConstantUtils.TIPOS_USUARIO_VALIDOS.contains(dto.getNomeTipo())) {
            throw new TipoUsuarioNotExistException(ConstantUtils.TIPO_USUARIO_INVALIDO);
        }
        if (tipoUsuarioRepository.existsByNomeTipo(dto.getNomeTipo())) {
            throw new TipoUsuarioExistException(ConstantUtils.TIPO_USUARIO_EXISTENTE);
        }

        try {
            TipoUsuarioEntity entity = TipoUsuarioMapper.INSTANCE.requestToEntity(dto);
            tipoUsuarioRepository.save(entity);
            return new MensagemResponse(ConstantUtils.TIPO_USUARIO_CADASTRADO);
        } catch (Exception e) {
            log.error("Erro ao cadastrar tipo de usuário", e);
            throw new ErroInternoException("Erro ao cadastrar tipo de usuário: " + e.getMessage());
        }
    }

    @Override
    public MensagemResponse atualizarTipoUsuario(Integer id, TipoUsuarioRequest dto) {
        TipoUsuarioEntity entity = tipoUsuarioRepository.findById(id)
                .orElseThrow(() -> new TipoUsuarioNotExistException(ConstantUtils.TIPO_USUARIO_NAO_ENCONTRADO));

        entity.setNomeTipo(dto.getNomeTipo());
        tipoUsuarioRepository.save(entity);
        return new MensagemResponse(ConstantUtils.TIPO_USUARIO_ATUALIZADO);
    }


    @Override
    public List<TipoUsuarioResponse> listarTipoUsuario() {
        List<TipoUsuarioEntity> entidades = tipoUsuarioRepository.findAllByOrderByIdAsc();
        return TipoUsuarioMapper.INSTANCE.entitiesToDTOs(entidades);
    }


    @Override
    public MensagemResponse deletarTipoUsuario(Integer id) {
        TipoUsuarioEntity entity = tipoUsuarioRepository.findById(id)
                .orElseThrow(() -> new TipoUsuarioNotExistException(ConstantUtils.TIPO_USUARIO_NAO_ENCONTRADO));

        try {
            tipoUsuarioRepository.delete(entity);
            return new MensagemResponse(ConstantUtils.TIPO_USUARIO_DELETADO);
        } catch (Exception e) {
            log.error("Erro ao deletar um tipo de usuário", e);
            throw new ErroInternoException("Erro ao deletar um tipo de usuário: " + e.getMessage());
        }
    }

    @Override
    public UsuarioResponse associarTipoUsuarioAoUsuario(String nomeTipo, Integer idUsuario) {
        UsuarioEntity usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(ConstantUtils.USUARIO_NAO_ENCONTRADO));
        // Buscar o Tipo de Usuário pelo nome
        TipoUsuarioEntity tipoUsuario = tipoUsuarioRepository.findByNomeTipo(nomeTipo)
                .orElseThrow(() -> new TipoUsuarioNotExistException(ConstantUtils.TIPO_USUARIO_NAO_ENCONTRADO));

        try {
            usuario.setTipoUsuario(tipoUsuario);
            usuarioRepository.save(usuario);

            UsuarioResponse response = UsuarioMapper.INSTANCE.entityToResponse(usuario);
            response.setMensagem(ConstantUtils.TIPO_USUARIO_ASSOCIADO);

            return response;
        } catch (Exception e) {
            log.error("Erro ao associar tipo de usuário ao usuário", e);
            throw new ErroInternoException("Erro ao associar tipo de usuário ao usuário: " + e.getMessage());
        }
    }

    @Override
    public TipoUsuarioEntity buscarTipoUsuarioPorNome(String nomeTipo) {
        return tipoUsuarioRepository.findByNomeTipo(nomeTipo)
                .orElseThrow(() -> new TipoUsuarioNotExistException(ConstantUtils.TIPO_USUARIO_NAO_ENCONTRADO));
    }
}
