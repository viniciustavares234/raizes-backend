package br.com.raizes.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "movimentacoes_pontos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovimentacaoPontos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "carteira_id")
    private CarteiraPontos carteira;

    private Integer pontos;

    private String tipoMovimentacao; // ENTRADA ou SAIDA

    private LocalDateTime dataMovimentacao;

    private String descricao;
}
