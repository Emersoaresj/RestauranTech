package br.com.fiap.restaurantes.gerenciamento.infra.mapper;

import br.com.fiap.restaurantes.gerenciamento.application.dto.request.RestauranteRequest;
import br.com.fiap.restaurantes.gerenciamento.application.dto.response.RestauranteResponse;
import br.com.fiap.restaurantes.gerenciamento.domain.model.RestauranteEntity;
import br.com.fiap.restaurantes.gerenciamento.domain.model.UsuarioEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface RestauranteMapper {

    RestauranteMapper INSTANCE = Mappers.getMapper(RestauranteMapper.class);


    @Mapping(target = "idRestaurante", ignore = true)
    @Mapping(source = "idDonoRestaurante", target = "donoRestaurante.id")
    RestauranteEntity dtoToEntity(RestauranteRequest request);


    @Mapping(source = "donoRestaurante", target = "donoRestaurante", qualifiedByName = "mapDonoRestauranteToString")
    List<RestauranteResponse> entityToResponse(List<RestauranteEntity> entity);

    default String mapDonoRestauranteToString(UsuarioEntity usuario) {
        return usuario.getNome();
    }



}
