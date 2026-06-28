package br.com.raizes.dto;

import br.com.raizes.enums.StatusPedido;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class StatusUpdateDTO {
    @NotNull(message = "O status não pode ser nulo.")
    private StatusPedido status;
}
