package br.com.raizes.controller;

import br.com.raizes.dto.ErrorResponseDTO;
import br.com.raizes.dto.PedidoCreateDTO;
import br.com.raizes.dto.StatusUpdateDTO;
import br.com.raizes.dto.PedidoDTO;
import br.com.raizes.entity.Pedido;
import br.com.raizes.enums.CanalPedido;
import br.com.raizes.enums.StatusPedido;
import br.com.raizes.mapper.PedidoMapper;
import br.com.raizes.service.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;
    private final PedidoMapper pedidoMapper;

    @PostMapping
    @Operation(summary = "Cria um novo pedido", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<PedidoDTO> criar(@RequestBody PedidoCreateDTO dto) {
        Pedido pedido = pedidoMapper.toEntity(dto);
        Pedido pedidoSalvo = pedidoService.criar(pedido);
        return ResponseEntity.status(HttpStatus.CREATED).body(pedidoMapper.toDTO(pedidoSalvo));
    }

    @GetMapping
    @Operation(summary = "Lista todos os pedidos, com filtro opcional por canal", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<List<PedidoDTO>> listarTodos(@RequestParam(required = false) CanalPedido canal) {
        List<PedidoDTO> pedidos = pedidoService.listarTodos(canal).stream()
                .map(pedidoMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um pedido por ID", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<PedidoDTO> buscarPorId(@PathVariable Long id) {
        Pedido pedido = pedidoService.buscarPorId(id);
        return ResponseEntity.ok(pedidoMapper.toDTO(pedido));
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    @Operation(summary = "Atualiza o status de um pedido (Ex: EM_PREPARO, PRONTO, ENTREGUE)", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Status atualizado com sucesso")
    @ApiResponse(responseCode = "400", description = "Status inválido", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    @ApiResponse(responseCode = "403", description = "Acesso negado")
    @ApiResponse(responseCode = "404", description = "Pedido não encontrado", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    public ResponseEntity<PedidoDTO> atualizarStatus(@PathVariable Long id, @Valid @RequestBody StatusUpdateDTO statusUpdate) {
        Pedido pedidoAtualizado = pedidoService.atualizarStatus(id, statusUpdate.getStatus());
        return ResponseEntity.ok(pedidoMapper.toDTO(pedidoAtualizado));
    }

}
