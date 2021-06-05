package xyz.wendelsegadilha.service;

import xyz.wendelsegadilha.domain.entity.Pedido;
import xyz.wendelsegadilha.domain.enums.StatusPedido;
import xyz.wendelsegadilha.rest.dto.PedidoDTO;

import java.util.Optional;

public interface PedidoService {
    Pedido salvar(PedidoDTO dto);

    Optional<Pedido> obterPedidoCompelto(Integer id);

    void atualizaStatus(Integer id, StatusPedido statusPedido);

}
