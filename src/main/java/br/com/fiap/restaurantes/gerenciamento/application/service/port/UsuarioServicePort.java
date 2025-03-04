package br.com.fiap.restaurantes.gerenciamento.application.service.port;

import br.com.fiap.restaurantes.gerenciamento.application.dto.UsuarioDTO;
import br.com.fiap.restaurantes.gerenciamento.application.dto.request.AlterarSenhaRequest;
import br.com.fiap.restaurantes.gerenciamento.application.dto.request.AtualizarUsuarioRequest;
import br.com.fiap.restaurantes.gerenciamento.application.dto.request.ValidaLoginUsuarioRequest;
import br.com.fiap.restaurantes.gerenciamento.application.dto.response.AtualizarSenhaResponse;
import br.com.fiap.restaurantes.gerenciamento.application.dto.response.AtualizarUsuarioResponse;
import br.com.fiap.restaurantes.gerenciamento.application.dto.response.MensagemResponse;
import br.com.fiap.restaurantes.gerenciamento.application.dto.response.ValidaLoginUsuarioResponse;

public interface UsuarioServicePort {

    AtualizarUsuarioResponse cadastrarUsuario(UsuarioDTO usuarioDTO);

    AtualizarUsuarioResponse atualizarUsuario(Integer id, AtualizarUsuarioRequest request);

    AtualizarSenhaResponse alterarSenha(Integer id, AlterarSenhaRequest alterarSenhaRequest);

    ValidaLoginUsuarioResponse validarLogin(ValidaLoginUsuarioRequest validaLoginUsuarioRequest);

    MensagemResponse deletarUsuario(Integer id);
}
