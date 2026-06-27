package br.com.raizes.service;

import br.com.raizes.entity.Cliente;
import br.com.raizes.exception.ResourceNotFoundException;
import br.com.raizes.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public Cliente cadastrar(Cliente cliente) {
        // TODO: Adicionar validações de negócio, encriptação de senha, etc.
        return clienteRepository.save(cliente);
    }

    public Cliente buscarPorId(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com o ID: " + id));
    }

    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }

    public Cliente atualizar(Long id, Cliente clienteAtualizado) {
        Cliente cliente = buscarPorId(id);
        // TODO: Atualizar apenas os campos permitidos
        cliente.setNome(clienteAtualizado.getNome());
        cliente.setTelefone(clienteAtualizado.getTelefone());
        cliente.setStatus(clienteAtualizado.getStatus());
        return clienteRepository.save(cliente);
    }

    public void deletar(Long id) {
        Cliente cliente = buscarPorId(id);
        // Pode ser uma exclusão lógica (inativar) ou física
        clienteRepository.delete(cliente);
    }
}
