package br.com.raizes.mapper;

import br.com.raizes.dto.ItemPedidoCreateDTO;
import br.com.raizes.dto.ItemPedidoDTO;
import br.com.raizes.dto.PedidoCreateDTO;
import br.com.raizes.dto.PedidoDTO;
import br.com.raizes.entity.Cliente;
import br.com.raizes.entity.ItemPedido;
import br.com.raizes.entity.Pedido;
import br.com.raizes.entity.Produto;
import br.com.raizes.entity.Unidade;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PedidoMapper {

    public PedidoDTO toDTO(Pedido pedido) {
        if (pedido == null) return null;
        
        Long clienteId = pedido.getCliente() != null ? pedido.getCliente().getId() : null;
        Long unidadeId = pedido.getUnidade() != null ? pedido.getUnidade().getId() : null;
        
        List<ItemPedidoDTO> itensDTO = pedido.getItens() != null 
                ? pedido.getItens().stream().map(this::toItemDTO).collect(Collectors.toList())
                : Collections.emptyList();

        return new PedidoDTO(
                pedido.getId(),
                clienteId,
                unidadeId,
                pedido.getDataPedido(),
                pedido.getStatus(),
                pedido.getValorTotal(),
                itensDTO
        );
    }

    private ItemPedidoDTO toItemDTO(ItemPedido item) {
        if (item == null) return null;
        Long produtoId = item.getProduto() != null ? item.getProduto().getId() : null;
        return new ItemPedidoDTO(item.getId(), produtoId, item.getQuantidade(), item.getPrecoUnitario());
    }

    public Pedido toEntity(PedidoCreateDTO dto) {
        if (dto == null) return null;
        
        Pedido pedido = new Pedido();
        
        if (dto.getClienteId() != null) {
            Cliente cliente = new Cliente();
            cliente.setId(dto.getClienteId());
            pedido.setCliente(cliente);
        }
        
        if (dto.getUnidadeId() != null) {
            Unidade unidade = new Unidade();
            unidade.setId(dto.getUnidadeId());
            pedido.setUnidade(unidade);
        }
        
        if (dto.getItens() != null) {
            List<ItemPedido> itens = dto.getItens().stream()
                    .map(itemDto -> toItemEntity(itemDto, pedido))
                    .collect(Collectors.toList());
            pedido.setItens(itens);
        }
        
        return pedido;
    }

    private ItemPedido toItemEntity(ItemPedidoCreateDTO dto, Pedido pedido) {
        if (dto == null) return null;
        ItemPedido item = new ItemPedido();
        
        if (dto.getProdutoId() != null) {
            Produto produto = new Produto();
            produto.setId(dto.getProdutoId());
            item.setProduto(produto);
        }
        
        item.setQuantidade(dto.getQuantidade());
        item.setPedido(pedido);
        return item;
    }
}
