package br.com.raizes.service;

import br.com.raizes.dto.EstoqueDTO;
import br.com.raizes.entity.Estoque;
import br.com.raizes.entity.MovimentacaoEstoque;
import br.com.raizes.entity.Produto;
import br.com.raizes.enums.TipoMovimentacao;
import br.com.raizes.exception.InsufficientStockException;
import br.com.raizes.exception.ResourceNotFoundException;
import br.com.raizes.repository.EstoqueRepository;
import br.com.raizes.repository.MovimentacaoEstoqueRepository;
import br.com.raizes.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class EstoqueService {

    private final EstoqueRepository estoqueRepository;
    private final MovimentacaoEstoqueRepository movimentacaoEstoqueRepository;
    private final ProdutoRepository produtoRepository;

    public EstoqueDTO consultarEstoque(Long unidadeId, Long produtoId) {
        Estoque estoque = findEstoque(unidadeId, produtoId);
        return toDTO(estoque);
    }

    @Transactional
    public EstoqueDTO darEntrada(Long unidadeId, Long produtoId, Integer quantidade) {
        Estoque estoque = findEstoque(unidadeId, produtoId);
        estoque.setQuantidade(estoque.getQuantidade() + quantidade);
        estoqueRepository.save(estoque);

        registrarMovimentacao(produtoId, quantidade, TipoMovimentacao.ENTRADA, "Entrada manual de estoque");

        return toDTO(estoque);
    }

    @Transactional
    public EstoqueDTO darSaida(Long unidadeId, Long produtoId, Integer quantidade) {
        Estoque estoque = findEstoque(unidadeId, produtoId);
        if (estoque.getQuantidade() < quantidade) {
            throw new InsufficientStockException("Saída não permitida. Quantidade em estoque: " + estoque.getQuantidade());
        }
        estoque.setQuantidade(estoque.getQuantidade() - quantidade);
        estoqueRepository.save(estoque);

        registrarMovimentacao(produtoId, quantidade, TipoMovimentacao.SAIDA, "Saída manual de estoque");

        return toDTO(estoque);
    }

    private void registrarMovimentacao(Long produtoId, Integer quantidade, TipoMovimentacao tipo, String observacao) {
        Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com o ID: " + produtoId));

        MovimentacaoEstoque movimentacao = new MovimentacaoEstoque();
        movimentacao.setProduto(produto);
        movimentacao.setQuantidade(quantidade);
        movimentacao.setTipo(tipo);
        movimentacao.setDataMovimentacao(LocalDateTime.now());
        movimentacao.setObservacao(observacao);
        // movimentacao.setUsuario(usuario); // TODO: Adicionar o usuário logado
        movimentacaoEstoqueRepository.save(movimentacao);
    }

    private Estoque findEstoque(Long unidadeId, Long produtoId) {
        return estoqueRepository.findByProdutoIdAndUnidadeId(produtoId, unidadeId)
                .orElseThrow(() -> new ResourceNotFoundException("Estoque não encontrado para o produto ID: " + produtoId + " na unidade ID: " + unidadeId));
    }

    private EstoqueDTO toDTO(Estoque estoque) {
        return new EstoqueDTO(
                estoque.getProduto().getId(),
                estoque.getProduto().getNome(),
                estoque.getUnidade().getId(),
                estoque.getQuantidade()
        );
    }
}
