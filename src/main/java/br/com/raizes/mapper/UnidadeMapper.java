package br.com.raizes.mapper;

import br.com.raizes.dto.UnidadeCreateDTO;
import br.com.raizes.dto.UnidadeDTO;
import br.com.raizes.entity.Unidade;
import org.springframework.stereotype.Component;

@Component
public class UnidadeMapper {

    public UnidadeDTO toDTO(Unidade unidade) {
        if (unidade == null) return null;
        return new UnidadeDTO(
                unidade.getId(),
                unidade.getNome(),
                unidade.getCnpj(),
                unidade.getEndereco(),
                unidade.getTelefone(),
                unidade.getStatus()
        );
    }

    public Unidade toEntity(UnidadeCreateDTO dto) {
        if (dto == null) return null;
        Unidade unidade = new Unidade();
        unidade.setNome(dto.getNome());
        unidade.setCnpj(dto.getCnpj());
        unidade.setEndereco(dto.getEndereco());
        unidade.setTelefone(dto.getTelefone());
        unidade.setStatus(dto.getStatus());
        return unidade;
    }
}
