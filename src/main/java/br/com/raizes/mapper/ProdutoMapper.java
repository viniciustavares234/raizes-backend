package br.com.raizes.mapper;

import br.com.raizes.dto.ProdutoCreateDTO;
import br.com.raizes.dto.ProdutoDTO;
import br.com.raizes.entity.Categoria;
import br.com.raizes.entity.Produto;
import org.springframework.stereotype.Component;

@Component
public class ProdutoMapper {

    public ProdutoDTO toDTO(Produto produto) {
        if (produto == null) return null;
        Long categoriaId = produto.getCategoria() != null ? produto.getCategoria().getId() : null;
        return new ProdutoDTO(
                produto.getId(),
                produto.getNome(),
                produto.getDescricao(),
                produto.getPrecoBase(),
                produto.getAtivo(),
                categoriaId
        );
    }

    public Produto toEntity(ProdutoCreateDTO dto) {
        if (dto == null) return null;
        Produto produto = new Produto();
        produto.setNome(dto.getNome());
        produto.setDescricao(dto.getDescricao());
        produto.setPrecoBase(dto.getPrecoBase());
        
        if (dto.getCategoriaId() != null) {
            Categoria categoria = new Categoria();
            categoria.setId(dto.getCategoriaId());
            produto.setCategoria(categoria);
        }
        
        return produto;
    }
}
