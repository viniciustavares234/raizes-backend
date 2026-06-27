package br.com.raizes.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClienteCreateDTO {
    private String nome;
    private String cpf;
    private String email;
    private String senha;
    private String telefone;
    private Boolean consentimento;
}
