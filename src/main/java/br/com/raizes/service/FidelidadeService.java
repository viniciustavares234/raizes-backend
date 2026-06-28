package br.com.raizes.service;

import br.com.raizes.dto.CarteiraPontosDTO;
import br.com.raizes.entity.CarteiraPontos;
import br.com.raizes.entity.MovimentacaoPontos;
import br.com.raizes.exception.InsufficientStockException;
import br.com.raizes.exception.ResourceNotFoundException;
import br.com.raizes.repository.CarteiraPontosRepository;
import br.com.raizes.repository.MovimentacaoPontosRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class FidelidadeService {

    private final CarteiraPontosRepository carteiraRepository;
    private final MovimentacaoPontosRepository movimentacaoRepository;

    public CarteiraPontosDTO consultarPontos(Long usuarioId) {
        CarteiraPontos carteira = carteiraRepository.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Carteira de pontos não encontrada para o cliente ID: " + usuarioId));

        return new CarteiraPontosDTO(usuarioId, carteira.getSaldo());
    }

    @Transactional
    public CarteiraPontosDTO resgatarPontos(Long usuarioId, Integer pontos, String descricao) {
        CarteiraPontos carteira = carteiraRepository.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Carteira de pontos não encontrada para o cliente ID: " + usuarioId));

        if (carteira.getSaldo() < pontos) {
            throw new InsufficientStockException("Saldo de pontos insuficiente. Saldo atual: " + carteira.getSaldo());
        }

        carteira.setSaldo(carteira.getSaldo() - pontos);
        carteiraRepository.save(carteira);

        MovimentacaoPontos movimentacao = new MovimentacaoPontos();
        movimentacao.setCarteira(carteira);
        movimentacao.setPontos(pontos);
        movimentacao.setTipoMovimentacao("SAIDA");
        movimentacao.setDataMovimentacao(LocalDateTime.now());
        movimentacao.setDescricao(descricao);
        movimentacaoRepository.save(movimentacao);

        return new CarteiraPontosDTO(usuarioId, carteira.getSaldo());
    }
}
