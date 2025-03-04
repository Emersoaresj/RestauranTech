package br.com.fiap.restaurantes.gerenciamento.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TipoUsuarioResponse {

    private Integer id;
    private String nomeTipo;
}
