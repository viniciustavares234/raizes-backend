package br.com.raizes.service;

import br.com.raizes.entity.Pedido;
import br.com.raizes.enums.StatusPedido;
import br.com.raizes.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;

    public Pedido criar(Pedido pedido) {
        // TODO: Validar cliente, estoque, calcular valor total
        pedido.setDataPedido(LocalDateTime.now());
        pedido.setStatus(StatusPedido.AGUARDANDO_PAGAMENTO);
        
        if (pedido.getItens() != null) {
            pedido.getItens().forEach(item -> item.setPedido(pedido));
        }
        
        return pedidoRepository.save(pedido);
    }

    public Pedido buscarPorId(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
    }

    public List<Pedido> listarPorCliente(Long clienteId) {
        return pedidoRepository.findByClienteId(clienteId);
    }

    public Pedido atualizarStatus(Long id, StatusPedido novoStatus) {
        Pedido pedido = buscarPorId(id);
        // TODO: Validar transições de status permitidas
        pedido.setStatus(novoStatus);
        return pedidoRepository.save(pedido);
    }
    
    public Pedido cancelar(Long id) {
        return atualizarStatus(id, StatusPedido.CANCELADO);
        // TODO: Retornar itens para o estoque, reembolsar, etc.
    }
}
