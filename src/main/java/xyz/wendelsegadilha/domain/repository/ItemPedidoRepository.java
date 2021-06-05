package xyz.wendelsegadilha.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.wendelsegadilha.domain.entity.ItemPedido;

public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Integer> {
}
