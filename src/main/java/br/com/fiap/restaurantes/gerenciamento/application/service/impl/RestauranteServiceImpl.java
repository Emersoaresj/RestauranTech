package br.com.fiap.restaurantes.gerenciamento.application.service.impl;

import br.com.fiap.restaurantes.gerenciamento.application.dto.request.RestauranteRequest;
import br.com.fiap.restaurantes.gerenciamento.application.dto.response.MensagemResponse;
import br.com.fiap.restaurantes.gerenciamento.application.dto.response.RestauranteResponse;
import br.com.fiap.restaurantes.gerenciamento.application.service.port.RestauranteServicePort;
import br.com.fiap.restaurantes.gerenciamento.domain.RestauranteEntity;
import br.com.fiap.restaurantes.gerenciamento.infra.exception.ErroInternoException;
import br.com.fiap.restaurantes.gerenciamento.infra.exception.restaurante.RestauranteExistsException;
import br.com.fiap.restaurantes.gerenciamento.infra.exception.restaurante.RestauranteNotExistsException;
import br.com.fiap.restaurantes.gerenciamento.infra.exception.usuario.UsuarioNotExistsException;
import br.com.fiap.restaurantes.gerenciamento.infra.mapper.RestauranteMapper;
import br.com.fiap.restaurantes.gerenciamento.infra.repository.RestauranteRepository;
import br.com.fiap.restaurantes.gerenciamento.infra.repository.UsuarioRepository;
import br.com.fiap.restaurantes.gerenciamento.utils.ConstantUtils;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class RestauranteServiceImpl implements RestauranteServicePort {

    @Autowired
    private RestauranteRepository repository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    @Override
    public MensagemResponse cadastrarRestaurante(RestauranteRequest request) {

        usuarioRepository.findById(request.getIdDonoRestaurante())
                .orElseThrow(() -> new UsuarioNotExistsException(ConstantUtils.DONO_NAO_ENCONTRADO));

        if(repository.existsByNomeRestaurante(request.getNomeRestaurante())){
            throw new RestauranteExistsException(ConstantUtils.RESTAURANTE_EXISTENTE);
        }

        try {
            RestauranteEntity entity = RestauranteMapper.INSTANCE.dtoToEntity(request);
            repository.save(entity);
            return new MensagemResponse(ConstantUtils.RESTAURANTE_CADASTRADO);
        } catch (Exception e) {
            log.error("Erro ao cadastrar restaurante", e);
            throw new ErroInternoException("Erro ao cadastrar restaurante: " + e.getMessage());
        }
    }

    @Transactional
    @Override
    public MensagemResponse atualizarRestaurante(RestauranteRequest request, Integer idRestaurante) {

        repository.findById(idRestaurante).orElseThrow(() -> new RestauranteNotExistsException(ConstantUtils.RESTAURANTE_NAO_ENCONTRADO));

        usuarioRepository.findById(request.getIdDonoRestaurante())
                .orElseThrow(() -> new UsuarioNotExistsException(ConstantUtils.DONO_NAO_ENCONTRADO));

        try {
            RestauranteEntity entity = RestauranteMapper.INSTANCE.dtoToEntity(request);
            entity.setIdRestaurante(idRestaurante);
            repository.save(entity);
            return new MensagemResponse(ConstantUtils.RESTAURANTE_ATUALIZADO);
        } catch (Exception e) {
            log.error("Erro ao atualizar restaurante", e);
            throw new ErroInternoException("Erro ao atualizar restaurante: " + e.getMessage());
        }

    }

    @Override
    public List<RestauranteResponse> buscarRestaurantes() {
        List<RestauranteEntity> entity = repository.findAll();
        return RestauranteMapper.INSTANCE.entityToResponse(entity);
    }

    @Transactional
    @Override
    public MensagemResponse deletarRestaurante(Integer idRestaurante) {
        RestauranteEntity entity = repository.findById(idRestaurante)
                .orElseThrow(() -> new RestauranteNotExistsException(ConstantUtils.RESTAURANTE_NAO_ENCONTRADO));
        try {
            repository.delete(entity);
            return new MensagemResponse(ConstantUtils.RESTAURANTE_DELETADO);
        } catch (Exception e) {
            log.error("Erro ao deletar restaurante", e);
            throw new ErroInternoException("Erro ao deletar restaurante: " + e.getMessage());
        }
    }
}
