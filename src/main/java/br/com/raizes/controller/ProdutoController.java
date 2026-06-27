package br.com.raizes.controller;

import br.com.raizes.dto.ProdutoCreateDTO;
import br.com.raizes.dto.ProdutoDTO;
import br.com.raizes.entity.Produto;
import br.com.raizes.mapper.ProdutoMapper;
import br.com.raizes.service.ProdutoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/produtos")
@RequiredArgsConstructor
public class ProdutoController {

    private final ProdutoService produtoService;
    private final ProdutoMapper produtoMapper;

    @PostMapping
    public ResponseEntity<ProdutoDTO> cadastrar(@RequestBody ProdutoCreateDTO dto) {
        Produto produto = produtoMapper.toEntity(dto);
        Produto produtoSalvo = produtoService.cadastrar(produto);
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoMapper.toDTO(produtoSalvo));
    }

    @GetMapping
    public ResponseEntity<List<ProdutoDTO>> listarTodos() {
        List<ProdutoDTO> produtos = produtoService.listarTodos().stream()
                .map(produtoMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(produtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoDTO> buscarPorId(@PathVariable Long id) {
        Produto produto = produtoService.buscarPorId(id);
        return ResponseEntity.ok(produtoMapper.toDTO(produto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoDTO> atualizar(@PathVariable Long id, @RequestBody ProdutoCreateDTO dto) {
        Produto produtoAtualizado = produtoService.atualizar(id, produtoMapper.toEntity(dto));
        return ResponseEntity.ok(produtoMapper.toDTO(produtoAtualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        produtoService.ativarDesativar(id, false); // Exclusão lógica (desativar)
        return ResponseEntity.noContent().build();
    }
}
