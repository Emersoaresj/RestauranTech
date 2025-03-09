package br.com.fiap.restaurantes.gerenciamento.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "cardapios")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardapioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_item_cardapio")
    private Integer id;

    private String nome;
    private String descricao;
    private Double preco;

    @Column(name = "disponibilidade_consumo", nullable = false)
    private String disponibilidadeConsumo;

    @Column(name = "caminho_imagem")
    private String caminhoImagem;

}
