package br.com.raizes.mapper;

import br.com.raizes.dto.CategoriaCreateDTO;
import br.com.raizes.dto.CategoriaDTO;
import br.com.raizes.entity.Categoria;
import org.springframework.stereotype.Component;

@Component
public class CategoriaMapper {

    public CategoriaDTO toDTO(Categoria categoria) {
        if (categoria == null) return null;
        return new CategoriaDTO(
                categoria.getId(),
                categoria.getNome(),
                categoria.getDescricao()
        );
    }

    public Categoria toEntity(CategoriaCreateDTO dto) {
        if (dto == null) return null;
        Categoria categoria = new Categoria();
        categoria.setNome(dto.getNome());
        categoria.setDescricao(dto.getDescricao());
        return categoria;
    }
}
    