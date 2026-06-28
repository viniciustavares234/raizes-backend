package br.com.raizes.dto;

import br.com.raizes.enums.TipoMovimentacaoPontos;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovimentacaoPontosDTO {
    private Long id;
    private Integer pontos;
    private TipoMovimentacaoPontos tipoMovimentacao;
    private LocalDateTime dataMovimentacao;
    private String descricao;
}
