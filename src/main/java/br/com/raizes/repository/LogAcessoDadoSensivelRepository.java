package br.com.raizes.repository;

import br.com.raizes.entity.LogAcessoDadoSensivel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogAcessoDadoSensivelRepository extends JpaRepository<LogAcessoDadoSensivel, Long> {
}
