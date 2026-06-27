package br.com.raizes.controller;

import br.com.raizes.dto.UnidadeCreateDTO;
import br.com.raizes.dto.UnidadeDTO;
import br.com.raizes.entity.Unidade;
import br.com.raizes.mapper.UnidadeMapper;
import br.com.raizes.service.UnidadeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/unidades")
@RequiredArgsConstructor
public class UnidadeController {

    private final UnidadeService unidadeService;
    private final UnidadeMapper unidadeMapper;

    @PostMapping
    public ResponseEntity<UnidadeDTO> cadastrar(@RequestBody UnidadeCreateDTO dto) {
        Unidade unidade = unidadeMapper.toEntity(dto);
        Unidade unidadeSalva = unidadeService.cadastrar(unidade);
        return ResponseEntity.status(HttpStatus.CREATED).body(unidadeMapper.toDTO(unidadeSalva));
    }

    @GetMapping
    public ResponseEntity<List<UnidadeDTO>> listarTodas() {
        List<UnidadeDTO> unidades = unidadeService.listarTodas().stream()
                .map(unidadeMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(unidades);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UnidadeDTO> buscarPorId(@PathVariable Long id) {
        Unidade unidade = unidadeService.buscarPorId(id);
        return ResponseEntity.ok(unidadeMapper.toDTO(unidade));
    }
}
