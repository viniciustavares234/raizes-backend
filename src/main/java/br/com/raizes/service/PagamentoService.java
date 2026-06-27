package br.com.raizes.service;

import br.com.raizes.entity.Pagamento;
import br.com.raizes.entity.Pedido;
import br.com.raizes.enums.StatusPedido;
import br.com.raizes.repository.PagamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PagamentoService {

    private final PagamentoRepository pagamentoRepository;
    private final PedidoService pedidoService;

    public Pagamento processarPagamento(Pagamento pagamento) {
        // TODO: Integração com gateway de pagamento (Stripe, Mercado Pago, etc.)
        
        pagamento.setDataPagamento(LocalDateTime.now());
        pagamento.setStatus("APROVADO"); // Simulando sucesso
        
        Pagamento pagamentoSalvo = pagamentoRepository.save(pagamento);
        
        // Atualiza o status do pedido
        if (pagamento.getPedido() != null && pagamento.getPedido().getId() != null) {
            pedidoService.atualizarStatus(pagamento.getPedido().getId(), StatusPedido.PAGO);
        }
        
        return pagamentoSalvo;
    }

    public Pagamento buscarPorId(Long id) {
        return pagamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pagamento não encontrado"));
    }
    
    public Pagamento buscarPorPedido(Long pedidoId) {
        return pagamentoRepository.findByPedidoId(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pagamento não encontrado para este pedido"));
    }
}
