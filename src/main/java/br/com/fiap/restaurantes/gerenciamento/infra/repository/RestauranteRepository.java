package br.com.fiap.restaurantes.gerenciamento.infra.repository;

import br.com.fiap.restaurantes.gerenciamento.domain.RestauranteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestauranteRepository extends JpaRepository<RestauranteEntity, Integer> {

    boolean existsByNomeRestaurante(String nomeRestaurante);
}
