package br.com.fiap.restaurantes.gerenciamento.application.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RestauranteDTO {

    private String nome;
    private String endereco;
    private String tipoCozinha;
    private String horarioFuncionamento;
    private TipoUsuarioDTO tipoUsuarioDTO;
}
