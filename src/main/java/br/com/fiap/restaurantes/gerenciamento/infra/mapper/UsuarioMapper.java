package br.com.fiap.restaurantes.gerenciamento.infra.mapper;

import br.com.fiap.restaurantes.gerenciamento.domain.dto.UsuarioDTO;
import br.com.fiap.restaurantes.gerenciamento.domain.model.UsuarioEntity;
import br.com.fiap.restaurantes.gerenciamento.domain.request.AtualizarUsuarioRequest;
import br.com.fiap.restaurantes.gerenciamento.domain.response.AtualizarSenhaResponse;
import br.com.fiap.restaurantes.gerenciamento.domain.response.AtualizarUsuarioResponse;
import br.com.fiap.restaurantes.gerenciamento.domain.response.ValidaLoginUsuarioResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UsuarioMapper {

    UsuarioMapper INSTANCE = Mappers.getMapper(UsuarioMapper.class);


    @Mapping(source = "nome", target = "nome")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "login", target = "login")
    @Mapping(source = "senha", target = "senha")
    @Mapping(source = "endereco", target = "endereco")
    @Mapping(source = "tipo", target = "tipoUsuario")
    @Mapping(target = "dataUltimaAlteracao", ignore = true)
    UsuarioEntity dtoToEntity(UsuarioDTO usuarioDTO);

    @Mapping(source = "nome", target = "nome")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "login", target = "login")
    @Mapping(source = "senha", target = "senha")
    @Mapping(source = "endereco", target = "endereco")
    @Mapping(source = "tipoUsuario", target = "tipo")
    UsuarioDTO entityToDTO(UsuarioEntity usuarioEntity);

    @Mapping(source = "nome", target = "nome")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "login", target = "login")
    @Mapping(source = "endereco", target = "endereco")
    @Mapping(source = "tipo", target = "tipo")
    UsuarioDTO atualizarUsuarioRequestToDto(AtualizarUsuarioRequest request);

    @Mapping(source = "nome", target = "nome")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "login", target = "login")
    @Mapping(source = "endereco", target = "endereco")
    @Mapping(source = "tipoUsuario", target = "tipo")
    AtualizarUsuarioResponse entityToAtualizarUsuarioResponse(UsuarioEntity entity);

    //source é o nome do atributo da entidade e target é o nome do atributo do response
    @Mapping(source = "senha", target = "novaSenha")
    AtualizarSenhaResponse entityToAtualizarSenhaResponse(UsuarioEntity entity);

    @Mapping(source = "login", target = "login")
    ValidaLoginUsuarioResponse entityToValidaLoginUsuario(UsuarioEntity entity);

}
