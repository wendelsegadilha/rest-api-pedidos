package xyz.wendelsegadilha.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import xyz.wendelsegadilha.domain.entity.Cliente;
import xyz.wendelsegadilha.domain.entity.Pedido;

import java.util.List;
import java.util.Optional;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

    List<Pedido> findByCliente(Cliente cliente);

    //retorna o pedido com os seus itens
    @Query("select p from Pedido p left join fetch p.itens where p.id = :id")
    Optional<Pedido> findbyIdFetchItens(@Param("id") Integer id);
}
