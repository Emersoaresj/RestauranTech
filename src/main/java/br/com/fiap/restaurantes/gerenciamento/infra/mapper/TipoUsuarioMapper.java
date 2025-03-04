package br.com.fiap.restaurantes.gerenciamento.infra.mapper;

import br.com.fiap.restaurantes.gerenciamento.application.dto.request.TipoUsuarioRequest;
import br.com.fiap.restaurantes.gerenciamento.application.dto.response.TipoUsuarioResponse;
import br.com.fiap.restaurantes.gerenciamento.domain.model.TipoUsuarioEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface TipoUsuarioMapper {

    TipoUsuarioMapper INSTANCE = Mappers.getMapper(TipoUsuarioMapper.class);


    @Mapping(source = "nomeTipo", target = "nomeTipo")
    @Mapping(target = "id", ignore = true)
    TipoUsuarioEntity requestToEntity(TipoUsuarioRequest request);

    List<TipoUsuarioResponse> entitiesToDTOs(List<TipoUsuarioEntity> entities);

}
