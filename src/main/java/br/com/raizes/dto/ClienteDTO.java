package br.com.raizes.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDTO {
    private Long id;
    private String nome;
    private String cpf;
    private String email;
    // Omitindo a senha por segurança
    private String telefone;
    private LocalDate dataCadastro;
    private String status;
    private Boolean consentimento;
}
