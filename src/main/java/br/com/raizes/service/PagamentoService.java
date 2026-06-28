package br.com.raizes.service;

import br.com.raizes.entity.Pagamento;
import br.com.raizes.enums.StatusPagamento;
import br.com.raizes.enums.StatusPedido;
import br.com.raizes.exception.ResourceNotFoundException;
import br.com.raizes.repository.PagamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PagamentoService {

    private final PagamentoRepository pagamentoRepository;
    private final PedidoService pedidoService;

    @Transactional
    public Pagamento criarPagamento(Pagamento pagamento) {
        pagamento.setStatus(StatusPagamento.PENDENTE);
        return pagamentoRepository.save(pagamento);
    }

    @Transactional
    public Pagamento confirmarPagamento(Long id) {
        Pagamento pagamento = buscarPorId(id);
        pagamento.setStatus(StatusPagamento.APROVADO);
        pagamento.setDataPagamento(LocalDateTime.now());

        if (pagamento.getPedido() != null && pagamento.getPedido().getId() != null) {
            pedidoService.atualizarStatus(pagamento.getPedido().getId(), StatusPedido.PAGO);
        }

        return pagamentoRepository.save(pagamento);
    }
    
    @Transactional
    public Pagamento simularPagamento(Pagamento pagamento) {
        pagamento.setStatus(StatusPagamento.APROVADO);
        pagamento.setDataPagamento(LocalDateTime.now());
        
        Pagamento pagamentoSalvo = pagamentoRepository.save(pagamento);

        if (pagamento.getPedido() != null && pagamento.getPedido().getId() != null) {
            pedidoService.atualizarStatus(pagamento.getPedido().getId(), StatusPedido.PAGO);
        }
        
        return pagamentoSalvo;
    }

    public Pagamento buscarPorId(Long id) {
        return pagamentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pagamento não encontrado"));
    }
    
    public Pagamento buscarPorPedido(Long pedidoId) {
        return pagamentoRepository.findByPedidoId(pedidoId)
                .orElseThrow(() -> new ResourceNotFoundException("Pagamento não encontrado para este pedido"));
    }
}
