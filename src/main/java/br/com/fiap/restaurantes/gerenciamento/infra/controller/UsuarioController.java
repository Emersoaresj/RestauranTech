package br.com.fiap.restaurantes.gerenciamento.infra.controller;

import br.com.fiap.restaurantes.gerenciamento.application.dto.UsuarioDTO;
import br.com.fiap.restaurantes.gerenciamento.application.dto.request.AlterarSenhaRequest;
import br.com.fiap.restaurantes.gerenciamento.application.dto.request.AtualizarUsuarioRequest;
import br.com.fiap.restaurantes.gerenciamento.application.dto.request.ValidaLoginUsuarioRequest;
import br.com.fiap.restaurantes.gerenciamento.application.dto.response.AtualizarSenhaResponse;
import br.com.fiap.restaurantes.gerenciamento.application.dto.response.AtualizarUsuarioResponse;
import br.com.fiap.restaurantes.gerenciamento.application.dto.response.MensagemResponse;
import br.com.fiap.restaurantes.gerenciamento.application.dto.response.ValidaLoginUsuarioResponse;
import br.com.fiap.restaurantes.gerenciamento.application.service.port.UsuarioServicePort;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioServicePort usuarioService;

    @PostMapping("/cadastrar")
    public ResponseEntity<AtualizarUsuarioResponse> cadastrarUsuario(@Valid @RequestBody UsuarioDTO usuarioDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.cadastrarUsuario(usuarioDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AtualizarUsuarioResponse> atualizarUsuario(@PathVariable Integer id, @Valid @RequestBody AtualizarUsuarioRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.atualizarUsuario(id, request));
    }

    @PostMapping("/alterar-senha/{id}")
    public ResponseEntity<AtualizarSenhaResponse> alterarSenha(@PathVariable Integer id, @RequestBody AlterarSenhaRequest alterarSenhaRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.alterarSenha(id, alterarSenhaRequest));
    }

    @PostMapping("/validar-login")
    public ResponseEntity<ValidaLoginUsuarioResponse> validarLogin(@Valid @RequestBody ValidaLoginUsuarioRequest validaLoginUsuarioRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.validarLogin(validaLoginUsuarioRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MensagemResponse> deletarUsuario(@PathVariable Integer id) {
        MensagemResponse mensagemResponse = usuarioService.deletarUsuario(id);
        return ResponseEntity.status(HttpStatus.OK).body(mensagemResponse);
    }
}
