package br.com.fiap.restaurantes.gerenciamento.application.dto.response;

import lombok.Data;

@Data
public class CardapioResponse {
    private Integer id;
    private String nome;
    private String descricao;
    private Double preco;
    private String disponibilidadeConsumo;
    private String caminhoImagem;
}
