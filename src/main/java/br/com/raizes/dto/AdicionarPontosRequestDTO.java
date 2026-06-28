package br.com.raizes.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdicionarPontosRequestDTO {

    @NotNull(message = "O ID do usuário é obrigatório.")
    private Long usuarioId;

    @NotNull(message = "A quantidade de pontos é obrigatória.")
    @Min(value = 1, message = "A quantidade de pontos deve ser no mínimo 1.")
    private Integer pontos;

    @NotBlank(message = "A descrição é obrigatória.")
    private String descricao;
}
