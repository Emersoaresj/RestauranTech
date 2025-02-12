package br.com.fiap.restaurantes.gerenciamento.service;

import br.com.fiap.restaurantes.gerenciamento.infra.exception.SenhaIncorretaException;
import br.com.fiap.restaurantes.gerenciamento.infra.exception.UsuarioExistException;
import br.com.fiap.restaurantes.gerenciamento.infra.exception.UsuarioNaoEncontradoException;
import br.com.fiap.restaurantes.gerenciamento.infra.mapper.UsuarioMapper;
import br.com.fiap.restaurantes.gerenciamento.domain.model.UsuarioEntity;
import br.com.fiap.restaurantes.gerenciamento.infra.repository.UsuarioRepository;
import br.com.fiap.restaurantes.gerenciamento.domain.request.AlterarSenhaRequest;
import br.com.fiap.restaurantes.gerenciamento.domain.dto.UsuarioDTO;
import br.com.fiap.restaurantes.gerenciamento.domain.request.AtualizarUsuarioRequest;
import br.com.fiap.restaurantes.gerenciamento.domain.response.AtualizarSenhaResponse;
import br.com.fiap.restaurantes.gerenciamento.domain.response.AtualizarUsuarioResponse;
import br.com.fiap.restaurantes.gerenciamento.domain.response.MensagemResponse;
import br.com.fiap.restaurantes.gerenciamento.domain.response.ValidaLoginUsuarioResponse;
import br.com.fiap.restaurantes.gerenciamento.utils.ConstantUtils;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;


    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public AtualizarUsuarioResponse cadastrarUsuario(UsuarioDTO usuarioDTO) {

        if (usuarioRepository.existsByLogin(usuarioDTO.getLogin())) {
            throw new UsuarioExistException(ConstantUtils.LOGIN_CADASTRADO);
        }

        try {
            UsuarioEntity entity = UsuarioMapper.INSTANCE.dtoToEntity(usuarioDTO);
            entity.setDataUltimaAlteracao(LocalDateTime.now());
            usuarioRepository.save(entity);

            AtualizarUsuarioResponse response = UsuarioMapper.INSTANCE.entityToAtualizarUsuarioResponse(entity);
            response.setMensagem(ConstantUtils.USUARIO_CADASTRADO);
            return response;
        } catch (Exception e) {
            log.error("Erro ao cadastrar usuário", e);
            throw new RuntimeException("Erro ao cadastrar usuário"); //TODO - Criar exceção personalizada
        }
    }

    @Transactional
    public AtualizarUsuarioResponse atualizarUsuario(Long id, AtualizarUsuarioRequest request) {

        UsuarioEntity usuarioEntity = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(ConstantUtils.USUARIO_NAO_ENCONTRADO));

        try {
            usuarioEntity.setNome(request.getNome());
            usuarioEntity.setEmail(request.getEmail());
            usuarioEntity.setLogin(request.getLogin());
            usuarioEntity.setEndereco(request.getEndereco());
            usuarioEntity.setTipoUsuario(request.getTipo());
            usuarioEntity.setDataUltimaAlteracao(LocalDateTime.now());
            usuarioRepository.save(usuarioEntity);

            AtualizarUsuarioResponse response = UsuarioMapper.INSTANCE.entityToAtualizarUsuarioResponse(usuarioEntity);
            response.setMensagem(ConstantUtils.USUARIO_ATUALIZADO);

            return response;
        } catch (UsuarioNaoEncontradoException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar usuário"); //TODO - Criar exceção personalizada
        }
    }

    @Transactional
    public AtualizarSenhaResponse alterarSenha(Long id, AlterarSenhaRequest alterarSenhaRequest) {

        UsuarioEntity usuarioEntity = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(ConstantUtils.USUARIO_NAO_ENCONTRADO));

        try {
            if (usuarioEntity.getSenha().equals(alterarSenhaRequest.getSenhaAntiga())) {
                usuarioEntity.setSenha(alterarSenhaRequest.getNovaSenha());
                usuarioEntity.setDataUltimaAlteracao(LocalDateTime.now());
                usuarioRepository.save(usuarioEntity);

                AtualizarSenhaResponse response = UsuarioMapper.INSTANCE.entityToAtualizarSenhaResponse(usuarioEntity);
                response.setMensagem(ConstantUtils.SENHA_ALTERADA);

                return response;
            } else {
                throw new SenhaIncorretaException(ConstantUtils.ERRO_SENHA);
            }
        } catch (UsuarioNaoEncontradoException | SenhaIncorretaException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao alterar senha"); //TODO - Criar exceção personalizada
        }
    }


    public ValidaLoginUsuarioResponse validarLogin(String login, String senha) {
        UsuarioEntity usuarioEntity = usuarioRepository.findByLogin(login)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(ConstantUtils.ERRO_LOGIN));

        try{
            if (!usuarioEntity.getSenha().equals(senha)) {
                throw new SenhaIncorretaException(ConstantUtils.ERRO_SENHA);
            }

            ValidaLoginUsuarioResponse response = UsuarioMapper.INSTANCE.entityToValidaLoginUsuario(usuarioEntity);
            response.setMensagem(ConstantUtils.LOGIN_VALIDADO);
            return response;
        } catch (UsuarioNaoEncontradoException | SenhaIncorretaException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao validar login"); //TODO - Criar exceção personalizada
        }
    }


    @Transactional
    public MensagemResponse deletarUsuario(Long id) {

        UsuarioEntity entity = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(ConstantUtils.USUARIO_NAO_ENCONTRADO));

        try {
            usuarioRepository.delete(entity);
            return new MensagemResponse(ConstantUtils.USUARIO_DELETADO);
        } catch (UsuarioNaoEncontradoException e) {
            throw e;
        } catch (Exception e){
            throw new RuntimeException("Erro ao deletar usuário"); //TODO - Criar exceção personalizada
        }
    }


}
