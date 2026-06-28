package br.com.raizes.service;

import br.com.raizes.dto.CarteiraPontosDTO;
import br.com.raizes.dto.MovimentacaoPontosDTO;
import br.com.raizes.entity.CarteiraPontos;
import br.com.raizes.entity.MovimentacaoPontos;
import br.com.raizes.enums.TipoMovimentacaoPontos;
import br.com.raizes.exception.InsufficientStockException;
import br.com.raizes.exception.ResourceNotFoundException;
import br.com.raizes.repository.CarteiraPontosRepository;
import br.com.raizes.repository.MovimentacaoPontosRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FidelidadeService {

    private final CarteiraPontosRepository carteiraRepository;
    private final MovimentacaoPontosRepository movimentacaoRepository;

    public CarteiraPontosDTO consultarPontos(Long usuarioId) {
        CarteiraPontos carteira = findCarteiraByUsuarioId(usuarioId);
        return new CarteiraPontosDTO(usuarioId, carteira.getSaldo());
    }

    @Transactional
    public CarteiraPontosDTO adicionarPontos(Long usuarioId, Integer pontos, String descricao) {
        CarteiraPontos carteira = findCarteiraByUsuarioId(usuarioId);
        carteira.setSaldo(carteira.getSaldo() + pontos);
        carteiraRepository.save(carteira);

        registrarMovimentacao(carteira, pontos, TipoMovimentacaoPontos.ENTRADA, descricao);

        return new CarteiraPontosDTO(usuarioId, carteira.getSaldo());
    }

    @Transactional
    public CarteiraPontosDTO resgatarPontos(Long usuarioId, Integer pontos, String descricao) {
        CarteiraPontos carteira = findCarteiraByUsuarioId(usuarioId);

        if (carteira.getSaldo() < pontos) {
            throw new InsufficientStockException("Saldo de pontos insuficiente. Saldo atual: " + carteira.getSaldo());
        }

        carteira.setSaldo(carteira.getSaldo() - pontos);
        carteiraRepository.save(carteira);

        registrarMovimentacao(carteira, pontos, TipoMovimentacaoPontos.SAIDA, descricao);

        return new CarteiraPontosDTO(usuarioId, carteira.getSaldo());
    }

    public List<MovimentacaoPontosDTO> listarHistorico(Long usuarioId) {
        CarteiraPontos carteira = findCarteiraByUsuarioId(usuarioId);
        List<MovimentacaoPontos> movimentacoes = movimentacaoRepository.findByCarteiraIdOrderByDataMovimentacaoDesc(carteira.getId());

        return movimentacoes.stream()
                .map(this::toMovimentacaoDTO)
                .collect(Collectors.toList());
    }

    private void registrarMovimentacao(CarteiraPontos carteira, Integer pontos, TipoMovimentacaoPontos tipo, String descricao) {
        MovimentacaoPontos movimentacao = new MovimentacaoPontos();
        movimentacao.setCarteira(carteira);
        movimentacao.setPontos(pontos);
        movimentacao.setTipoMovimentacao(tipo);
        movimentacao.setDataMovimentacao(LocalDateTime.now());
        movimentacao.setDescricao(descricao);
        movimentacaoRepository.save(movimentacao);
    }

    private CarteiraPontos findCarteiraByUsuarioId(Long usuarioId) {
        return carteiraRepository.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Carteira de pontos não encontrada para o usuário ID: " + usuarioId));
    }

    private MovimentacaoPontosDTO toMovimentacaoDTO(MovimentacaoPontos movimentacao) {
        return new MovimentacaoPontosDTO(
                movimentacao.getId(),
                movimentacao.getPontos(),
                movimentacao.getTipoMovimentacao(),
                movimentacao.getDataMovimentacao(),
                movimentacao.getDescricao()
        );
    }
}
