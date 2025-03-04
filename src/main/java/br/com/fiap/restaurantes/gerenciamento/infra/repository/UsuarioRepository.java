package br.com.fiap.restaurantes.gerenciamento.infra.repository;

import br.com.fiap.restaurantes.gerenciamento.domain.model.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Integer> {

    boolean existsByLogin(String login);
    Optional<UsuarioEntity> findByLogin(String login);
}
