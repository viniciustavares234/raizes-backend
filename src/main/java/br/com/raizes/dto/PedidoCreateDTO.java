package br.com.raizes.dto;

import br.com.raizes.enums.CanalPedido;
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
    private CanalPedido canalPedido;
    private List<ItemPedidoCreateDTO> itens;
}
