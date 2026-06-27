package br.com.raizes.service;

import br.com.raizes.entity.Produto;
import br.com.raizes.exception.ResourceNotFoundException;
import br.com.raizes.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public Produto cadastrar(Produto produto) {
        // TODO: Validar categoria existente, valores, etc.
        produto.setAtivo(true);
        return produtoRepository.save(produto);
    }

    public Produto buscarPorId(Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com o ID: " + id));
    }

    public List<Produto> listarTodos(Long unidadeId) {
        if (unidadeId != null) {
            return produtoRepository.findByUnidadeId(unidadeId);
        }
        return produtoRepository.findAll();
    }

    public Produto atualizar(Long id, Produto produtoAtualizado) {
        Produto produto = buscarPorId(id);
        produto.setNome(produtoAtualizado.getNome());
        produto.setDescricao(produtoAtualizado.getDescricao());
        produto.setPrecoBase(produtoAtualizado.getPrecoBase());
        produto.setCategoria(produtoAtualizado.getCategoria());
        return produtoRepository.save(produto);
    }

    public void ativarDesativar(Long id, boolean ativo) {
        Produto produto = buscarPorId(id);
        produto.setAtivo(ativo);
        produtoRepository.save(produto);
    }
}
