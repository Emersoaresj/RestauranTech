package br.com.fiap.restaurantes.gerenciamento.application.service.impl;

import br.com.fiap.restaurantes.gerenciamento.application.dto.UsuarioDTO;
import br.com.fiap.restaurantes.gerenciamento.application.dto.request.AlterarSenhaRequest;
import br.com.fiap.restaurantes.gerenciamento.application.dto.request.AtualizarUsuarioRequest;
import br.com.fiap.restaurantes.gerenciamento.application.dto.request.ValidaLoginUsuarioRequest;
import br.com.fiap.restaurantes.gerenciamento.application.dto.response.AtualizarSenhaResponse;
import br.com.fiap.restaurantes.gerenciamento.application.dto.response.MensagemResponse;
import br.com.fiap.restaurantes.gerenciamento.application.dto.response.UsuarioResponse;
import br.com.fiap.restaurantes.gerenciamento.application.dto.response.ValidaLoginUsuarioResponse;
import br.com.fiap.restaurantes.gerenciamento.application.service.port.TipoUsuarioServicePort;
import br.com.fiap.restaurantes.gerenciamento.application.service.port.UsuarioServicePort;
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
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class UsuarioServiceImpl implements UsuarioServicePort {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TipoUsuarioServicePort tipoUsuarioService;


    @Override
    public List<UsuarioResponse> listarUsuarios() {
        List<UsuarioEntity> entity = usuarioRepository.findAll();
        return UsuarioMapper.INSTANCE.entityToResponse(entity);
    }


    @Transactional
    @Override
    public MensagemResponse cadastrarUsuario(UsuarioDTO usuarioDTO) {

        if (usuarioRepository.existsByLogin(usuarioDTO.getLogin())) {
            throw new UsuarioExistException(ConstantUtils.LOGIN_CADASTRADO);
        }

        try {
            TipoUsuarioEntity tipoUsuario = tipoUsuarioService.buscarTipoUsuarioPorNome(usuarioDTO.getTipoUsuario().getNomeTipo());
            UsuarioEntity entity = UsuarioMapper.INSTANCE.dtoToEntity(usuarioDTO);
            entity.setTipoUsuario(tipoUsuario);
            entity.setDataUltimaAlteracao(LocalDateTime.now());
            usuarioRepository.save(entity);

            return new MensagemResponse(ConstantUtils.USUARIO_CADASTRADO);
        } catch (TipoUsuarioNotExistException e) {
            throw e;
        } catch (Exception e) {
            log.error("Erro ao cadastrar usuário", e);
            throw new ErroInternoException("Erro ao cadastrar usuário: " + e.getMessage());
        }
    }

    @Transactional
    @Override
    public MensagemResponse atualizarUsuario(Integer id, AtualizarUsuarioRequest request) {

        UsuarioEntity usuarioEntity = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNotExistsException(ConstantUtils.USUARIO_NAO_ENCONTRADO));

        try {
            TipoUsuarioEntity tipoUsuario = tipoUsuarioService.buscarTipoUsuarioPorNome(request.getTipoUsuario().getNomeTipo());

            UsuarioMapper.INSTANCE.atualizarUsuarioRequestToEntity(request, usuarioEntity);

            usuarioEntity.setSenha(usuarioEntity.getSenha());
            usuarioEntity.setTipoUsuario(tipoUsuario);
            usuarioEntity.setDataUltimaAlteracao(LocalDateTime.now());
            usuarioRepository.save(usuarioEntity);

            return new MensagemResponse(ConstantUtils.USUARIO_ATUALIZADO);
        } catch (TipoUsuarioNotExistException e) {
            throw e;
        } catch (Exception e) {
            log.error("Erro ao atualizar usuário", e);
            throw new ErroInternoException("Erro ao atualizar usuário: " + e.getMessage());
        }
    }

    @Transactional
    @Override
    public AtualizarSenhaResponse alterarSenha(Integer id, AlterarSenhaRequest alterarSenhaRequest) {

        UsuarioEntity usuarioEntity = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNotExistsException(ConstantUtils.USUARIO_NAO_ENCONTRADO));

        if (!usuarioEntity.getSenha().equals(alterarSenhaRequest.getSenhaAntiga())) {
            throw new SenhaIncorretaException(ConstantUtils.ERRO_SENHA);
        }

        try {
            usuarioEntity.setSenha(alterarSenhaRequest.getNovaSenha());
            usuarioEntity.setDataUltimaAlteracao(LocalDateTime.now());
            usuarioRepository.save(usuarioEntity);

            AtualizarSenhaResponse response = UsuarioMapper.INSTANCE.entityToAtualizarSenhaResponse(usuarioEntity);
            response.setMensagem(ConstantUtils.SENHA_ALTERADA);
            return response;

        } catch (Exception e) {
            log.error("Erro ao alterar senha do usuário", e);
            throw new ErroInternoException("Erro ao alterar senha: " + e.getMessage());
        }
    }

    @Override
    public ValidaLoginUsuarioResponse validarLogin(ValidaLoginUsuarioRequest validaLoginUsuarioRequest) {
        UsuarioEntity usuarioEntity = usuarioRepository.findByLogin(validaLoginUsuarioRequest.getLogin())
                .orElseThrow(() -> new UsuarioNotExistsException(ConstantUtils.ERRO_LOGIN));

        if (!usuarioEntity.getSenha().equals(validaLoginUsuarioRequest.getSenha())) {
            throw new SenhaIncorretaException(ConstantUtils.ERRO_SENHA);
        }

        try {
            ValidaLoginUsuarioResponse response = UsuarioMapper.INSTANCE.entityToValidaLoginUsuario(usuarioEntity);
            response.setMensagem(ConstantUtils.LOGIN_VALIDADO);
            return response;
        } catch (Exception e) {
            log.error("Erro ao validar login do usuário", e);
            throw new ErroInternoException("Erro ao validar login: " + e.getMessage());
        }
    }


    @Transactional
    @Override
    public MensagemResponse deletarUsuario(Integer id) {

        UsuarioEntity entity = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNotExistsException(ConstantUtils.USUARIO_NAO_ENCONTRADO));

        try {
            usuarioRepository.delete(entity);
            return new MensagemResponse(ConstantUtils.USUARIO_DELETADO);
        } catch (Exception e) {
            log.error("Erro ao deletar usuário", e);
            throw new ErroInternoException("Erro ao deletar usuário: " + e.getMessage());
        }
    }


}
