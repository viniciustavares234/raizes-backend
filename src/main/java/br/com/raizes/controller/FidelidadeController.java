package br.com.raizes.controller;

import br.com.raizes.dto.AdicionarPontosRequestDTO;
import br.com.raizes.dto.CarteiraPontosDTO;
import br.com.raizes.dto.MovimentacaoPontosDTO;
import br.com.raizes.dto.ResgatePontosRequestDTO;
import br.com.raizes.service.FidelidadeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fidelidade")
@RequiredArgsConstructor
public class FidelidadeController {

    private final FidelidadeService fidelidadeService;

    @GetMapping("/{usuarioId}/pontos")
    @Operation(summary = "Consulta o saldo de pontos de um usuário")
    public ResponseEntity<CarteiraPontosDTO> consultarPontos(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(fidelidadeService.consultarPontos(usuarioId));
    }
    
    @GetMapping("/{usuarioId}/historico")
    @Operation(summary = "Lista o histórico de movimentação de pontos de um usuário")
    public ResponseEntity<List<MovimentacaoPontosDTO>> listarHistorico(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(fidelidadeService.listarHistorico(usuarioId));
    }

    @PutMapping("/pontos")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Adiciona pontos para um usuário", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<CarteiraPontosDTO> adicionarPontos(@Valid @RequestBody AdicionarPontosRequestDTO request) {
        CarteiraPontosDTO carteira = fidelidadeService.adicionarPontos(request.getUsuarioId(), request.getPontos(), request.getDescricao());
        return ResponseEntity.ok(carteira);
    }

    @PostMapping("/resgatar")
    @Operation(summary = "Resgata (utiliza) pontos de um usuário", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<CarteiraPontosDTO> resgatarPontos(@Valid @RequestBody ResgatePontosRequestDTO request) {
        CarteiraPontosDTO carteira = fidelidadeService.resgatarPontos(request.getUsuarioId(), request.getPontos(), request.getDescricao());
        return ResponseEntity.ok(carteira);
    }
}
