package br.com.fiap.restaurantes.gerenciamento.infra.controller;

import br.com.fiap.restaurantes.gerenciamento.application.dto.TipoUsuarioDTO;
import br.com.fiap.restaurantes.gerenciamento.application.dto.request.TipoUsuarioRequest;
import br.com.fiap.restaurantes.gerenciamento.application.dto.response.MensagemResponse;
import br.com.fiap.restaurantes.gerenciamento.application.dto.response.TipoUsuarioResponse;
import br.com.fiap.restaurantes.gerenciamento.application.service.port.TipoUsuarioServicePort;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tipos-usuario")
public class TipoUsuarioController {

    @Autowired
    private TipoUsuarioServicePort tipoUsuarioService;

    @PostMapping
    public ResponseEntity<MensagemResponse> cadastrarTipoUsuario(@Valid @RequestBody TipoUsuarioRequest dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tipoUsuarioService.cadastrarTipoUsuario(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MensagemResponse> atualizarTipoUsuario(@PathVariable Integer id, @Valid @RequestBody TipoUsuarioRequest dto) {
        return ResponseEntity.ok(tipoUsuarioService.atualizarTipoUsuario(id, dto));
    }

    @GetMapping
    public ResponseEntity<List<TipoUsuarioResponse>> listarTipoUsuario() {
        return ResponseEntity.ok(tipoUsuarioService.listarTipoUsuario());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MensagemResponse> deletarTipoUsuario(@PathVariable Integer id) {
      return ResponseEntity.ok(tipoUsuarioService.deletarTipoUsuario(id));
    }
}

