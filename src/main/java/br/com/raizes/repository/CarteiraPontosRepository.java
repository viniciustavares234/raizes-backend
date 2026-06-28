package br.com.raizes.repository;

import br.com.raizes.entity.CarteiraPontos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarteiraPontosRepository extends JpaRepository<CarteiraPontos, Long> {
    Optional<CarteiraPontos> findByUsuarioId(Long usuarioId);
}
