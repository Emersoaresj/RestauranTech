package br.com.fiap.restaurantes.gerenciamento.infra.repository;

import br.com.fiap.restaurantes.gerenciamento.domain.CardapioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardapioRepository extends JpaRepository<CardapioEntity, Integer> {

    boolean existsByNome(String nome);
}
