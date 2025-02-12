package br.com.fiap.restaurantes.gerenciamento.controller;

import br.com.fiap.restaurantes.gerenciamento.domain.request.AlterarSenhaRequest;
import br.com.fiap.restaurantes.gerenciamento.domain.dto.UsuarioDTO;
import br.com.fiap.restaurantes.gerenciamento.domain.request.AtualizarUsuarioRequest;
import br.com.fiap.restaurantes.gerenciamento.domain.response.AtualizarSenhaResponse;
import br.com.fiap.restaurantes.gerenciamento.domain.response.AtualizarUsuarioResponse;
import br.com.fiap.restaurantes.gerenciamento.domain.response.MensagemResponse;
import br.com.fiap.restaurantes.gerenciamento.domain.response.ValidaLoginUsuarioResponse;
import br.com.fiap.restaurantes.gerenciamento.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // Cadastrar novo usuário
    @PostMapping("/cadastrar")
    public ResponseEntity<AtualizarUsuarioResponse> cadastrarUsuario(@Valid @RequestBody UsuarioDTO usuarioDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.cadastrarUsuario(usuarioDTO));
    }

    // Atualizar dados do usuário
    @PutMapping("/{id}")
    public ResponseEntity<AtualizarUsuarioResponse> atualizarUsuario(@PathVariable Long id, @Valid @RequestBody AtualizarUsuarioRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.atualizarUsuario(id, request));
    }

    // Alterar senha do usuário
    @PostMapping("/alterar-senha/{id}")
    public ResponseEntity<AtualizarSenhaResponse> alterarSenha(@PathVariable Long id, @RequestBody AlterarSenhaRequest alterarSenhaRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.alterarSenha(id, alterarSenhaRequest));
    }

    // Validar login do usuário
    @PostMapping("/validar-login")
    public ResponseEntity<ValidaLoginUsuarioResponse> validarLogin(@RequestParam String login, //TODO - Alterar para RequestBody
                                                                   @RequestParam String senha) {
        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.validarLogin(login, senha));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MensagemResponse> deletarUsuario(@PathVariable Long id) {
        MensagemResponse mensagemResponse = usuarioService.deletarUsuario(id);
        return ResponseEntity.status(HttpStatus.OK).body(mensagemResponse);
    }
}
