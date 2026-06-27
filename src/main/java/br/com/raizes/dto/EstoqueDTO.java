package br.com.raizes.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EstoqueDTO {
    private Long produtoId;
    private String nomeProduto;
    private Long unidadeId;
    private Integer quantidade;
}
