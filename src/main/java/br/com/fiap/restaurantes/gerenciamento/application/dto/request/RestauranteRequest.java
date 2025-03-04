package br.com.fiap.restaurantes.gerenciamento.application.dto.request;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class RestauranteRequest {

    private String nome;
    private String endereco;
    private String tipoCozinha;
    private Timestamp horarioFuncionamento;
    private Integer donoId;
}
