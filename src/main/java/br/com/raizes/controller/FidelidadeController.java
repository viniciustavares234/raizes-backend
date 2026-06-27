package br.com.raizes.controller;

import br.com.raizes.dto.CarteiraPontosDTO;
import br.com.raizes.dto.ResgatePontosRequestDTO;
import br.com.raizes.service.FidelidadeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fidelidade")
@RequiredArgsConstructor
public class FidelidadeController {

    private final FidelidadeService fidelidadeService;

    @GetMapping("/{clienteId}/pontos")
    public ResponseEntity<CarteiraPontosDTO> consultarPontos(@PathVariable Long clienteId) {
        return ResponseEntity.ok(fidelidadeService.consultarPontos(clienteId));
    }

    @PostMapping("/resgatar")
    public ResponseEntity<CarteiraPontosDTO> resgatarPontos(@RequestBody ResgatePontosRequestDTO request) {
        return ResponseEntity.ok(fidelidadeService.resgatarPontos(request.getClienteId(), request.getPontos(), request.getDescricao()));
    }
}
