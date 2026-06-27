package br.com.raizes.service;

import br.com.raizes.entity.Unidade;
import br.com.raizes.repository.UnidadeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UnidadeService {

    private final UnidadeRepository unidadeRepository;

    public Unidade cadastrar(Unidade unidade) {
        return unidadeRepository.save(unidade);
    }

    public Unidade buscarPorId(Long id) {
        return unidadeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Unidade não encontrada"));
    }

    public List<Unidade> listarTodas() {
        return unidadeRepository.findAll();
    }
}
