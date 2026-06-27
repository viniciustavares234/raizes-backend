package br.com.raizes.mapper;

import br.com.raizes.dto.PagamentoCreateDTO;
import br.com.raizes.dto.PagamentoDTO;
import br.com.raizes.entity.Pagamento;
import br.com.raizes.entity.Pedido;
import org.springframework.stereotype.Component;

@Component
public class PagamentoMapper {

    public PagamentoDTO toDTO(Pagamento pagamento) {
        if (pagamento == null) return null;
        
        Long pedidoId = pagamento.getPedido() != null ? pagamento.getPedido().getId() : null;
        
        return new PagamentoDTO(
                pagamento.getId(),
                pedidoId,
                pagamento.getMetodoPagamento(),
                pagamento.getValor(),
                pagamento.getDataPagamento(),
                pagamento.getStatus()
        );
    }

    public Pagamento toEntity(PagamentoCreateDTO dto) {
        if (dto == null) return null;
        
        Pagamento pagamento = new Pagamento();
        pagamento.setMetodoPagamento(dto.getMetodoPagamento());
        pagamento.setValor(dto.getValor());
        
        if (dto.getPedidoId() != null) {
            Pedido pedido = new Pedido();
            pedido.setId(dto.getPedidoId());
            pagamento.setPedido(pedido);
        }
        
        return pagamento;
    }
}
