package br.com.raizes.service;

import br.com.raizes.entity.Estoque;
import br.com.raizes.entity.ItemPedido;
import br.com.raizes.entity.Pedido;
import br.com.raizes.entity.Produto;
import br.com.raizes.entity.Unidade;
import br.com.raizes.exception.InsufficientStockException;
import br.com.raizes.repository.EstoqueRepository;
import br.com.raizes.repository.PedidoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PedidoServiceTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private EstoqueRepository estoqueRepository;

    @InjectMocks
    private PedidoService pedidoService;

    private Pedido pedido;
    private Estoque estoque;

    @BeforeEach
    void setUp() {
        Unidade unidade = new Unidade();
        unidade.setId(1L);

        Produto produto = new Produto();
        produto.setId(1L);
        produto.setNome("Queijo Coalho");

        ItemPedido itemPedido = new ItemPedido();
        itemPedido.setProduto(produto);
        itemPedido.setQuantidade(10);

        pedido = new Pedido();
        pedido.setUnidade(unidade);
        pedido.setItens(Collections.singletonList(itemPedido));

        estoque = new Estoque();
        estoque.setProduto(produto);
        estoque.setUnidade(unidade);
    }

    @Test
    void deveLancarExcecaoQuandoEstoqueForInsuficiente() {
        // Arrange
        estoque.setQuantidade(5); // Menos do que o pedido (10)
        when(estoqueRepository.findByProdutoIdAndUnidadeId(anyLong(), anyLong())).thenReturn(Optional.of(estoque));

        // Act & Assert
        assertThrows(InsufficientStockException.class, () -> {
            pedidoService.criar(pedido);
        });

        // Verify
        verify(pedidoRepository, never()).save(any(Pedido.class));
    }

    @Test
    void deveCriarPedidoQuandoEstoqueForSuficiente() {
        // Arrange
        estoque.setQuantidade(20); // Mais do que o pedido (10)
        when(estoqueRepository.findByProdutoIdAndUnidadeId(anyLong(), anyLong())).thenReturn(Optional.of(estoque));
        when(pedidoRepository.save(any(Pedido.class))).thenReturn(pedido);

        // Act
        Pedido pedidoCriado = pedidoService.criar(pedido);

        // Assert
        assertNotNull(pedidoCriado);
        assertEquals(10, estoque.getQuantidade()); // Verifica se o estoque foi abatido

        // Verify
        verify(estoqueRepository, times(1)).save(estoque);
        verify(pedidoRepository, times(1)).save(pedido);
    }
}
