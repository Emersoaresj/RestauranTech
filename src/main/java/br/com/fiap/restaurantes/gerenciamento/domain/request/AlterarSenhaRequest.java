package br.com.fiap.restaurantes.gerenciamento.domain.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlterarSenhaRequest {
    private String senhaAntiga;
    private String novaSenha;
}

