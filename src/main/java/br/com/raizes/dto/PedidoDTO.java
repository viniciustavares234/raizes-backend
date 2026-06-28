package br.com.raizes.dto;

import br.com.raizes.enums.CanalPedido;
import br.com.raizes.enums.FormaPagamento;
import br.com.raizes.enums.StatusPedido;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PedidoDTO {
    private Long id;
    private Long usuarioId;
    private Long unidadeId;
    private LocalDateTime dataCriacao;
    private StatusPedido status;
    private BigDecimal valorTotal;
    private CanalPedido canalPedido;
    private FormaPagamento formaPagamento;
    private List<ItemPedidoDTO> itens;
}
