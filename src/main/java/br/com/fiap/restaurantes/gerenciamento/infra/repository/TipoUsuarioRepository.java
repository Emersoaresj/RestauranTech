package br.com.fiap.restaurantes.gerenciamento.infra.repository;

import br.com.fiap.restaurantes.gerenciamento.domain.model.TipoUsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TipoUsuarioRepository extends JpaRepository<TipoUsuarioEntity, Integer> {

    boolean existsByNomeTipo(String tipo);

    Optional<TipoUsuarioEntity> findByNomeTipo(String nomeTipo);

    List<TipoUsuarioEntity> findAllByOrderByIdAsc();
}
