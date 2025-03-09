package br.com.fiap.restaurantes.gerenciamento.infra.repository;

import br.com.fiap.restaurantes.gerenciamento.domain.TipoUsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TipoUsuarioRepository extends JpaRepository<TipoUsuarioEntity, Integer> {

    @Query("SELECT t FROM TipoUsuarioEntity t WHERE LOWER(t.nomeTipo) = LOWER(:nomeTipo)")
    Optional<TipoUsuarioEntity> findByNomeTipo(String nomeTipo);
}
