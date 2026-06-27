package br.com.raizes.repository;

import br.com.raizes.entity.MovimentacaoPontos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovimentacaoPontosRepository extends JpaRepository<MovimentacaoPontos, Long> {
    List<MovimentacaoPontos> findByCarteiraId(Long carteiraId);
}
