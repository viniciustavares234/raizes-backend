package br.com.raizes.dto;

import br.com.raizes.enums.CanalPedido;
import br.com.raizes.enums.FormaPagamento;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PedidoCreateDTO {
    private Long usuarioId;
    private Long unidadeId;

    @NotNull(message = "O canal do pedido é obrigatório.")
    private CanalPedido canalPedido;

    @NotNull(message = "A forma de pagamento é obrigatória.")
    private FormaPagamento formaPagamento;

    @NotNull(message = "A lista de itens não pode ser nula.")
    private List<ItemPedidoCreateDTO> itens;
}
