package br.com.raizes.controller;

import br.com.raizes.dto.PagamentoCreateDTO;
import br.com.raizes.dto.PagamentoDTO;
import br.com.raizes.entity.Pagamento;
import br.com.raizes.mapper.PagamentoMapper;
import br.com.raizes.service.PagamentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pagamentos")
@RequiredArgsConstructor
public class PagamentoController {

    private final PagamentoService pagamentoService;
    private final PagamentoMapper pagamentoMapper;

    @PostMapping
    public ResponseEntity<PagamentoDTO> processarPagamento(@RequestBody PagamentoCreateDTO dto) {
        Pagamento pagamento = pagamentoMapper.toEntity(dto);
        Pagamento pagamentoProcessado = pagamentoService.processarPagamento(pagamento);
        return ResponseEntity.status(HttpStatus.CREATED).body(pagamentoMapper.toDTO(pagamentoProcessado));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PagamentoDTO> buscarPorId(@PathVariable Long id) {
        Pagamento pagamento = pagamentoService.buscarPorId(id);
        return ResponseEntity.ok(pagamentoMapper.toDTO(pagamento));
    }
    
    @GetMapping("/pedido/{pedidoId}")
    public ResponseEntity<PagamentoDTO> buscarPorPedido(@PathVariable Long pedidoId) {
        Pagamento pagamento = pagamentoService.buscarPorPedido(pedidoId);
        return ResponseEntity.ok(pagamentoMapper.toDTO(pagamento));
    }
}
