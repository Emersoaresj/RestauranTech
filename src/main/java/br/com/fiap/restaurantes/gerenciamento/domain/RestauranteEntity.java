package br.com.fiap.restaurantes.gerenciamento.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "restaurante")
public class RestauranteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_restaurante")
    private Integer idRestaurante;

    @Column(name = "nome_restaurante")
    private String nomeRestaurante;

    @Column(name = "endereco_restaurante")
    private String enderecoRestaurante;

    @Column(name = "tipo_cozinha")
    private String tipoCozinha;

    @Column(name = "horario_funcionamento")
    private String horarioFuncionamento;

    @ManyToOne
    @JoinColumn(name = "dono_restaurante_id", nullable = false)
    private UsuarioEntity donoRestaurante;
}
