package br.com.fiap.restaurantes.gerenciamento.infra.mapper;

import br.com.fiap.restaurantes.gerenciamento.application.dto.UsuarioDTO;
import br.com.fiap.restaurantes.gerenciamento.application.dto.response.UsuarioResponse;
import br.com.fiap.restaurantes.gerenciamento.domain.model.UsuarioEntity;
import br.com.fiap.restaurantes.gerenciamento.application.dto.request.AtualizarUsuarioRequest;
import br.com.fiap.restaurantes.gerenciamento.application.dto.response.AtualizarSenhaResponse;
import br.com.fiap.restaurantes.gerenciamento.application.dto.response.ValidaLoginUsuarioResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UsuarioMapper {

    UsuarioMapper INSTANCE = Mappers.getMapper(UsuarioMapper.class);


    @Mapping(source = "nome", target = "nome")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "login", target = "login")
    @Mapping(source = "senha", target = "senha")
    @Mapping(source = "endereco", target = "endereco")
    @Mapping(target = "dataUltimaAlteracao", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "tipoUsuario", ignore = true)  // Não mapeamos o tipo diretamente, isso será feito na service
    UsuarioEntity dtoToEntity(UsuarioDTO usuarioDTO);

    @Mapping(source = "nome", target = "nome")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "login", target = "login")
    @Mapping(source = "senha", target = "senha")
    @Mapping(source = "endereco", target = "endereco")
    @Mapping(target = "tipoUsuario", ignore = true)
    UsuarioDTO entityToDTO(UsuarioEntity usuarioEntity);


    @Mapping(target = "dataUltimaAlteracao", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "tipoUsuario", ignore = true)
    @Mapping(target = "senha", ignore = true)
    void atualizarUsuarioRequestToEntity(AtualizarUsuarioRequest request, @MappingTarget UsuarioEntity entity);



    @Mapping(source = "senha", target = "novaSenha")
    @Mapping(target = "mensagem", ignore = true)
    AtualizarSenhaResponse entityToAtualizarSenhaResponse(UsuarioEntity entity);

    @Mapping(source = "login", target = "login")
    @Mapping(target = "mensagem", ignore = true)
    ValidaLoginUsuarioResponse entityToValidaLoginUsuario(UsuarioEntity entity);

    @Mapping(target = "mensagem", ignore = true)
    UsuarioResponse entityToResponse(UsuarioEntity entity);
}
