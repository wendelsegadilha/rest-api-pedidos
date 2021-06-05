package xyz.wendelsegadilha.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.wendelsegadilha.domain.entity.Cliente;
import xyz.wendelsegadilha.domain.entity.ItemPedido;
import xyz.wendelsegadilha.domain.entity.Pedido;
import xyz.wendelsegadilha.domain.entity.Produto;
import xyz.wendelsegadilha.domain.enums.StatusPedido;
import xyz.wendelsegadilha.domain.repository.ClienteRepository;
import xyz.wendelsegadilha.domain.repository.ItemPedidoRepository;
import xyz.wendelsegadilha.domain.repository.PedidoRepository;
import xyz.wendelsegadilha.domain.repository.ProdutoRepository;
import xyz.wendelsegadilha.exception.PedidoNaoEncontradoException;
import xyz.wendelsegadilha.exception.RegraNegocioException;
import xyz.wendelsegadilha.rest.dto.ItemPedidoDTO;
import xyz.wendelsegadilha.rest.dto.PedidoDTO;
import xyz.wendelsegadilha.service.PedidoService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PedidoServiceImpl implements PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;
    private final ProdutoRepository produtoRepository;
    private final ItemPedidoRepository itemPedidoRepository;

    @Override
    @Transactional
    public Pedido salvar(PedidoDTO dto) {
        Integer idCliente = dto.getCliente();
        Cliente cliente = clienteRepository.findById(idCliente)
                .orElseThrow(() -> new RegraNegocioException("Código de cliente inválido: " + idCliente));

        Pedido pedido = new Pedido();
        pedido.setTotal(dto.getTotal());
        pedido.setDataPedido(LocalDate.now());
        pedido.setCliente(cliente);
        pedido.setStatus(StatusPedido.REALIZADO);

        List<ItemPedido> itensPedido = converterItens(pedido, dto.getItens());
        pedidoRepository.save(pedido);
        itemPedidoRepository.saveAll(itensPedido);
        pedido.setItens(itensPedido);
        return pedido;
    }

    @Override
    public Optional<Pedido> obterPedidoCompelto(Integer id) {
        return pedidoRepository.findbyIdFetchItens(id);
    }

    @Override
    @Transactional
    public void atualizaStatus(Integer id, StatusPedido statusPedido) {
        pedidoRepository.findById(id).map(pedido -> {
            pedido.setStatus(statusPedido);
            return pedidoRepository.save(pedido);
        }).orElseThrow(() -> new PedidoNaoEncontradoException());
    }

    private List<ItemPedido> converterItens(Pedido pedido, List<ItemPedidoDTO> itens){
        if(itens.isEmpty()){
            throw new RegraNegocioException("Não há itens adicionado ao pedido.");
        }

        return itens
                .stream()
                .map(dto -> {
                    Integer idProduto =  dto.getProduto();
                    Produto produto = produtoRepository.findById(idProduto)
                            .orElseThrow(() -> new RegraNegocioException("Código de produto inválido: " + idProduto));
                    ItemPedido itemPedido = new ItemPedido();
                    itemPedido.setQuantidade(dto.getQuantidade());
                    itemPedido.setPedido(pedido);
                    itemPedido.setProduto(produto);
                    return itemPedido;
                }).collect(Collectors.toList());
    }
}
