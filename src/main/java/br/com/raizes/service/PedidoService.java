package br.com.raizes.service;

import br.com.raizes.entity.Estoque;
import br.com.raizes.entity.ItemPedido;
import br.com.raizes.entity.Pedido;
import br.com.raizes.enums.CanalPedido;
import br.com.raizes.enums.StatusPedido;
import br.com.raizes.exception.InsufficientStockException;
import br.com.raizes.exception.ResourceNotFoundException;
import br.com.raizes.repository.EstoqueRepository;
import br.com.raizes.repository.PedidoRepository;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.EnumSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final EstoqueRepository estoqueRepository;

    private static final Map<StatusPedido, EnumSet<StatusPedido>> TRANSICOES_PERMITIDAS = Map.of(
            StatusPedido.AGUARDANDO_PAGAMENTO, EnumSet.of(StatusPedido.PAGO, StatusPedido.CANCELADO),
            StatusPedido.PAGO, EnumSet.of(StatusPedido.EM_PREPARO, StatusPedido.CANCELADO),
            StatusPedido.EM_PREPARO, EnumSet.of(StatusPedido.EM_ROTA, StatusPedido.CANCELADO),
            StatusPedido.EM_ROTA, EnumSet.of(StatusPedido.ENTREGUE, StatusPedido.CANCELADO)
            // Status ENTREGUE e CANCELADO são finais, não podem transicionar para outros.
    );

    @Transactional
    public Pedido criar(Pedido pedido) {
        // Validar e abater estoque
        validarEstoque(pedido);

        // TODO: calcular valor total
        pedido.setDataPedido(LocalDateTime.now());
        pedido.setStatus(StatusPedido.AGUARDANDO_PAGAMENTO);
        
        if (pedido.getItens() != null) {
            pedido.getItens().forEach(item -> item.setPedido(pedido));
        }
        
        return pedidoRepository.save(pedido);
    }

    private void validarEstoque(Pedido pedido) {
        for (ItemPedido item : pedido.getItens()) {
            Long produtoId = item.getProduto().getId();
            Long unidadeId = pedido.getUnidade().getId();

            Estoque estoque = estoqueRepository.findByProdutoIdAndUnidadeId(produtoId, unidadeId)
                    .orElseThrow(() -> new ResourceNotFoundException("Estoque não encontrado para o produto ID: " + produtoId + " na unidade ID: " + unidadeId));

            if (estoque.getQuantidade() < item.getQuantidade()) {
                throw new InsufficientStockException("Estoque insuficiente para o produto: " + estoque.getProduto().getNome() + ". Quantidade disponível: " + estoque.getQuantidade());
            }

            // Abater do estoque
            estoque.setQuantidade(estoque.getQuantidade() - item.getQuantidade());
            estoqueRepository.save(estoque);
        }
    }

    public Pedido buscarPorId(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado com o ID: " + id));
    }

    public List<Pedido> listarTodos(CanalPedido canal) {
        return pedidoRepository.findAll((Specification<Pedido>) (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (canal != null) {
                predicates.add(criteriaBuilder.equal(root.get("canalPedido"), canal));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }

    public Pedido atualizarStatus(Long id, StatusPedido novoStatus) {
        Pedido pedido = buscarPorId(id);
        validarTransicaoStatus(pedido.getStatus(), novoStatus);
        pedido.setStatus(novoStatus);
        return pedidoRepository.save(pedido);
    }

    private void validarTransicaoStatus(StatusPedido statusAtual, StatusPedido novoStatus) {
        if (statusAtual == novoStatus) {
            return; // Nenhuma mudança, é válido.
        }

        EnumSet<StatusPedido> statusPermitidos = TRANSICOES_PERMITIDAS.get(statusAtual);

        if (statusPermitidos == null || !statusPermitidos.contains(novoStatus)) {
            throw new IllegalArgumentException("Transição de status inválida de " + statusAtual + " para " + novoStatus);
        }
    }
    
    @Transactional
    public Pedido cancelar(Long id) {
        Pedido pedido = atualizarStatus(id, StatusPedido.CANCELADO);
        // Retornar itens para o estoque
        retornarItensAoEstoque(pedido);
        return pedido;
    }

    private void retornarItensAoEstoque(Pedido pedido) {
        for (ItemPedido item : pedido.getItens()) {
            Long produtoId = item.getProduto().getId();
            Long unidadeId = pedido.getUnidade().getId();

            estoqueRepository.findByProdutoIdAndUnidadeId(produtoId, unidadeId)
                    .ifPresent(estoque -> {
                        estoque.setQuantidade(estoque.getQuantidade() + item.getQuantidade());
                        estoqueRepository.save(estoque);
                    });
        }
    }
}
