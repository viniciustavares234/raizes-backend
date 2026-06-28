package br.com.raizes.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "logs_acesso_dados_sensiveis")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LogAcessoDadoSensivel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dataHora;

    private String usuario;

    private String perfil;

    private String recurso;

    private Long recursoId;

    private String acao;

    @Column(length = 1000)
    private String camposAcessados;

    private String ip;
}
