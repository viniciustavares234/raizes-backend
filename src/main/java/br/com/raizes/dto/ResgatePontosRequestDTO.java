package br.com.raizes.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResgatePontosRequestDTO {
    private Long clienteId;
    private Integer pontos;
    private String descricao;
}
