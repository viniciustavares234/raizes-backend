package br.com.raizes.mapper;

import br.com.raizes.dto.UsuarioCreateDTO;
import br.com.raizes.dto.UsuarioDTO;
import br.com.raizes.entity.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    public UsuarioDTO toDTO(Usuario usuario) {
        if (usuario == null) return null;
        return new UsuarioDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getCpf(),
                usuario.getEmail(),
                usuario.getTelefone(),
                usuario.getDataCadastro(),
                usuario.getStatus(),
                usuario.getConsentimento(),
                usuario.getDataConsentimento(),
                usuario.getVersaoTermoConsentimento(),
                usuario.getFinalidadeConsentimento(),
                usuario.getBaseLegalConsentimento(),
                usuario.getDataAnonimizacao()
        );
    }

    public Usuario toEntity(UsuarioCreateDTO dto) {
        if (dto == null) return null;
        Usuario usuario = new Usuario();
        usuario.setNome(dto.getNome());
        usuario.setCpf(dto.getCpf());
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(dto.getSenha());
        usuario.setTelefone(dto.getTelefone());
        usuario.setConsentimento(dto.getConsentimento());
        usuario.setVersaoTermoConsentimento(dto.getVersaoTermoConsentimento());
        return usuario;
    }
}
