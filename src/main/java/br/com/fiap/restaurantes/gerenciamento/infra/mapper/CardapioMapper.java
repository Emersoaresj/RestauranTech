package br.com.fiap.restaurantes.gerenciamento.infra.mapper;

import br.com.fiap.restaurantes.gerenciamento.application.dto.request.CardapioRequest;
import br.com.fiap.restaurantes.gerenciamento.application.dto.response.CardapioResponse;
import br.com.fiap.restaurantes.gerenciamento.domain.CardapioEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface CardapioMapper {

    CardapioMapper INSTANCE = Mappers.getMapper(CardapioMapper.class);


    @Mapping(target = "id", ignore = true)
    CardapioEntity requestToEntity(CardapioRequest request);

    List<CardapioResponse> entityToResponse(List<CardapioEntity> entity);

}
