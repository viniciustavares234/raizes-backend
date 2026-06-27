package br.com.raizes.mapper;

import br.com.raizes.dto.ClienteCreateDTO;
import br.com.raizes.dto.ClienteDTO;
import br.com.raizes.entity.Cliente;
import org.springframework.stereotype.Component;

@Component
public class ClienteMapper {

    public ClienteDTO toDTO(Cliente cliente) {
        if (cliente == null) return null;
        return new ClienteDTO(
                cliente.getId(),
                cliente.getNome(),
                cliente.getCpf(),
                cliente.getEmail(),
                cliente.getTelefone(),
                cliente.getDataCadastro(),
                cliente.getStatus(),
                cliente.getConsentimento()
        );
    }

    public Cliente toEntity(ClienteCreateDTO dto) {
        if (dto == null) return null;
        Cliente cliente = new Cliente();
        cliente.setNome(dto.getNome());
        cliente.setCpf(dto.getCpf());
        cliente.setEmail(dto.getEmail());
        cliente.setSenha(dto.getSenha());
        cliente.setTelefone(dto.getTelefone());
        cliente.setConsentimento(dto.getConsentimento());
        return cliente;
    }
}
