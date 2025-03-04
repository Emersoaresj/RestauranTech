package br.com.fiap.restaurantes.gerenciamento.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tipos_usuario")
@AllArgsConstructor
@NoArgsConstructor
@Data

public class TipoUsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_usuario")
    private Integer id;

    @Column(nullable = false, unique = true)
    private String nomeTipo;
}
