package br.com.fiap.restaurantes.gerenciamento.infra.mapper;

import br.com.fiap.restaurantes.gerenciamento.application.dto.UsuarioDTO;
import br.com.fiap.restaurantes.gerenciamento.application.dto.response.UsuarioResponse;
import br.com.fiap.restaurantes.gerenciamento.domain.model.UsuarioEntity;
import br.com.fiap.restaurantes.gerenciamento.application.dto.request.AtualizarUsuarioRequest;
import br.com.fiap.restaurantes.gerenciamento.application.dto.response.AtualizarSenhaResponse;
import br.com.fiap.restaurantes.gerenciamento.application.dto.response.AtualizarUsuarioResponse;
import br.com.fiap.restaurantes.gerenciamento.application.dto.response.ValidaLoginUsuarioResponse;
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

    @Mapping(source = "nome", target = "nome")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "login", target = "login")
    @Mapping(source = "endereco", target = "endereco")
    @Mapping(source = "tipo", target = "tipoUsuario")
    @Mapping(target = "senha", ignore = true)
    UsuarioDTO atualizarUsuarioRequestToDto(AtualizarUsuarioRequest request);

    @Mapping(source = "nome", target = "nome")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "login", target = "login")
    @Mapping(source = "endereco", target = "endereco")
    @Mapping(source = "tipoUsuario.nomeTipo", target = "tipo.nomeTipo")
    @Mapping(target = "mensagem", ignore = true)
    AtualizarUsuarioResponse entityToAtualizarUsuarioResponse(UsuarioEntity entity);

    //source é o nome do atributo da entidade e target é o nome do atributo do response
    @Mapping(source = "senha", target = "novaSenha")
    @Mapping(target = "mensagem", ignore = true)
    AtualizarSenhaResponse entityToAtualizarSenhaResponse(UsuarioEntity entity);

    @Mapping(source = "login", target = "login")
    @Mapping(target = "mensagem", ignore = true)
    ValidaLoginUsuarioResponse entityToValidaLoginUsuario(UsuarioEntity entity);

    @Mapping(target = "mensagem", ignore = true)
    UsuarioResponse entityToResponse(UsuarioEntity entity);
}
