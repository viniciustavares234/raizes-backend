package br.com.raizes.controller;

import br.com.raizes.dto.CategoriaCreateDTO;
import br.com.raizes.dto.CategoriaDTO;
import br.com.raizes.entity.Categoria;
import br.com.raizes.mapper.CategoriaMapper;
import br.com.raizes.service.CategoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/categorias")
@RequiredArgsConstructor
public class CategoriaController {

    private final CategoriaService categoriaService;
    private final CategoriaMapper categoriaMapper;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Cria uma nova categoria", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<CategoriaDTO> cadastrar(@RequestBody CategoriaCreateDTO dto) {
        Categoria categoria = categoriaMapper.toEntity(dto);
        Categoria categoriaSalva = categoriaService.cadastrar(categoria);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaMapper.toDTO(categoriaSalva));
    }

    @GetMapping
    @Operation(summary = "Lista todas as categorias")
    public ResponseEntity<List<CategoriaDTO>> listarTodas() {
        List<CategoriaDTO> categorias = categoriaService.listarTodas().stream()
                .map(categoriaMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(categorias);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca uma categoria por ID")
    public ResponseEntity<CategoriaDTO> buscarPorId(@PathVariable Long id) {
        Categoria categoria = categoriaService.buscarPorId(id);
        return ResponseEntity.ok(categoriaMapper.toDTO(categoria));
    }
}
