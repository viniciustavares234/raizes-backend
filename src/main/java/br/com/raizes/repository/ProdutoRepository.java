package br.com.raizes.repository;

import br.com.raizes.entity.Produto;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long>, JpaSpecificationExecutor<Produto> {

    @Query("SELECT e.produto FROM Estoque e WHERE e.unidade.id = :unidadeId")
    List<Produto> findByUnidadeId(@Param("unidadeId") Long unidadeId);
}
