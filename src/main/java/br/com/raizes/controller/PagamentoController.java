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
    @Operation(summary = "Cria um novo pagamento com status PENDENTE")
    public ResponseEntity<PagamentoDTO> criarPagamento(@RequestBody PagamentoCreateDTO dto) {
        Pagamento pagamento = pagamentoMapper.toEntity(dto);
        Pagamento pagamentoProcessado = pagamentoService.criarPagamento(pagamento);
        return ResponseEntity.status(HttpStatus.CREATED).body(pagamentoMapper.toDTO(pagamentoProcessado));
    }

    @PostMapping("/simular")
    @Operation(summary = "Simula o processamento e aprovação de um pagamento")
    public ResponseEntity<PagamentoDTO> simularPagamento(@RequestBody PagamentoCreateDTO dto) {
        Pagamento pagamento = pagamentoMapper.toEntity(dto);
        Pagamento pagamentoProcessado = pagamentoService.simularPagamento(pagamento);
        return ResponseEntity.status(HttpStatus.CREATED).body(pagamentoMapper.toDTO(pagamentoProcessado));
    }
    
    @PostMapping("/{id}/confirmar")
    @Operation(summary = "Confirma um pagamento existente")
    public ResponseEntity<PagamentoDTO> confirmarPagamento(@PathVariable Long id) {
        Pagamento pagamento = pagamentoService.confirmarPagamento(id);
        return ResponseEntity.ok(pagamentoMapper.toDTO(pagamento));
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
