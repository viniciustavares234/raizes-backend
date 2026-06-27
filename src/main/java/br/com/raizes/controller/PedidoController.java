package br.com.raizes.controller;

import br.com.raizes.dto.PedidoCreateDTO;
import br.com.raizes.dto.PedidoDTO;
import br.com.raizes.entity.Pedido;
import br.com.raizes.enums.StatusPedido;
import br.com.raizes.mapper.PedidoMapper;
import br.com.raizes.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;
    private final PedidoMapper pedidoMapper;

    @PostMapping
    public ResponseEntity<PedidoDTO> criar(@RequestBody PedidoCreateDTO dto) {
        Pedido pedido = pedidoMapper.toEntity(dto);
        Pedido pedidoSalvo = pedidoService.criar(pedido);
        return ResponseEntity.status(HttpStatus.CREATED).body(pedidoMapper.toDTO(pedidoSalvo));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoDTO> buscarPorId(@PathVariable Long id) {
        Pedido pedido = pedidoService.buscarPorId(id);
        return ResponseEntity.ok(pedidoMapper.toDTO(pedido));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<PedidoDTO> atualizarStatus(@PathVariable Long id, @RequestBody StatusUpdateDTO statusUpdate) {
        Pedido pedidoAtualizado = pedidoService.atualizarStatus(id, statusUpdate.getStatus());
        return ResponseEntity.ok(pedidoMapper.toDTO(pedidoAtualizado));
    }

    // Classe auxiliar temporária
    public static class StatusUpdateDTO {
        private StatusPedido status;

        public StatusPedido getStatus() {
            return status;
        }

        public void setStatus(StatusPedido status) {
            this.status = status;
        }
    }
}
