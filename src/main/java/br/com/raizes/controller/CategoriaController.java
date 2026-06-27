package br.com.raizes.controller;

import br.com.raizes.dto.CategoriaCreateDTO;
import br.com.raizes.dto.CategoriaDTO;
import br.com.raizes.entity.Categoria;
import br.com.raizes.mapper.CategoriaMapper;
import br.com.raizes.service.CategoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<CategoriaDTO> cadastrar(@RequestBody CategoriaCreateDTO dto) {
        Categoria categoria = categoriaMapper.toEntity(dto);
        Categoria categoriaSalva = categoriaService.cadastrar(categoria);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaMapper.toDTO(categoriaSalva));
    }

    @GetMapping
    public ResponseEntity<List<CategoriaDTO>> listarTodas() {
        List<CategoriaDTO> categorias = categoriaService.listarTodas().stream()
                .map(categoriaMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(categorias);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaDTO> buscarPorId(@PathVariable Long id) {
        Categoria categoria = categoriaService.buscarPorId(id);
        return ResponseEntity.ok(categoriaMapper.toDTO(categoria));
    }
}
