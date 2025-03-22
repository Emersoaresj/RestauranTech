package br.com.fiap.restaurantes.gerenciamento.application.service.impl;

import br.com.fiap.restaurantes.gerenciamento.application.dto.request.CardapioRequest;
import br.com.fiap.restaurantes.gerenciamento.application.dto.response.CardapioResponse;
import br.com.fiap.restaurantes.gerenciamento.application.dto.response.MensagemResponse;
import br.com.fiap.restaurantes.gerenciamento.application.service.port.CardapioServicePort;
import br.com.fiap.restaurantes.gerenciamento.domain.CardapioEntity;
import br.com.fiap.restaurantes.gerenciamento.infra.exception.ErroInternoException;
import br.com.fiap.restaurantes.gerenciamento.infra.exception.cardapio.CardapioExistsException;
import br.com.fiap.restaurantes.gerenciamento.infra.exception.cardapio.CardapioNotExistsException;
import br.com.fiap.restaurantes.gerenciamento.infra.mapper.CardapioMapper;
import br.com.fiap.restaurantes.gerenciamento.infra.repository.CardapioRepository;
import br.com.fiap.restaurantes.gerenciamento.utils.ConstantUtils;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CardapioServiceImpl implements CardapioServicePort {

    @Autowired
    private CardapioRepository repository;

    @Transactional
    @Override
    public MensagemResponse cadastrarCardapio(CardapioRequest request) {

        if (repository.existsByNome(request.getNome())) {
            throw new CardapioExistsException(ConstantUtils.CARDAPIO_EXISTENTE);
        }

        try {
            CardapioEntity entity = CardapioMapper.INSTANCE.requestToEntity(request);
            repository.save(entity);
            return new MensagemResponse(ConstantUtils.CARDAPIO_CADASTRADO);
        } catch (Exception e) {
            log.error("Erro ao cadastrar cardápio", e);
            throw new ErroInternoException("Erro ao cadastrar cardápio: " + e.getMessage());
        }
    }

    @Transactional
    @Override
    public MensagemResponse atualizarCardapio(CardapioRequest request, Integer idCardapio) {
        repository.findById(idCardapio)
                .orElseThrow(() -> new CardapioNotExistsException(ConstantUtils.CARDAPIO_NAO_ENCONTRADO));

        try {
            CardapioEntity entity = CardapioMapper.INSTANCE.requestToEntity(request);
            entity.setId(idCardapio);
            repository.save(entity);
            return new MensagemResponse(ConstantUtils.CARDAPIO_ATUALIZADO);
        } catch (Exception e) {
            log.error("Erro ao atualizar cardápio", e);
            throw new ErroInternoException("Erro ao atualizar cardápio: " + e.getMessage());
        }
    }

    @Override
    public List<CardapioResponse> buscarCardapios() {
        List<CardapioEntity> cardapios = repository.findAll();
        return CardapioMapper.INSTANCE.entityToResponse(cardapios);
    }

    @Transactional
    @Override
    public MensagemResponse deletarCardapio(Integer idCardapio) {
        repository.findById(idCardapio)
                .orElseThrow(() -> new CardapioNotExistsException(ConstantUtils.CARDAPIO_NAO_ENCONTRADO));
        try {
            repository.deleteById(idCardapio);
            return new MensagemResponse(ConstantUtils.CARDAPIO_DELETADO);
        } catch (Exception e) {
            log.error("Erro ao deletar cardápio", e);
            throw new ErroInternoException("Erro ao deletar cardápio: " + e.getMessage());
        }
    }


}
