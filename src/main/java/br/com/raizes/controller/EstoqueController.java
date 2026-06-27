package br.com.raizes.controller;

import br.com.raizes.dto.EstoqueDTO;
import br.com.raizes.dto.MovimentacaoEstoqueRequestDTO;
import br.com.raizes.service.EstoqueService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/estoque")
@RequiredArgsConstructor
public class EstoqueController {

    private final EstoqueService estoqueService;

    @GetMapping("/{unidadeId}/{produtoId}")
    public ResponseEntity<EstoqueDTO> consultarEstoque(
            @PathVariable Long unidadeId,
            @PathVariable Long produtoId) {
        return ResponseEntity.ok(estoqueService.consultarEstoque(unidadeId, produtoId));
    }

    @PostMapping("/entrada")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Registra entrada de produtos no estoque", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<EstoqueDTO> darEntrada(@RequestBody MovimentacaoEstoqueRequestDTO request) {
        return ResponseEntity.ok(estoqueService.darEntrada(request.getUnidadeId(), request.getProdutoId(), request.getQuantidade()));
    }

    @PostMapping("/saida")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Registra saída de produtos do estoque", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<EstoqueDTO> darSaida(@RequestBody MovimentacaoEstoqueRequestDTO request) {
        return ResponseEntity.ok(estoqueService.darSaida(request.getUnidadeId(), request.getProdutoId(), request.getQuantidade()));
    }
}
