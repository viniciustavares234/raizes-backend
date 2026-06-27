package br.com.raizes.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UnidadeCreateDTO {
    private String nome;
    private String cnpj;
    private String endereco;
    private String telefone;
    private String status;
}
