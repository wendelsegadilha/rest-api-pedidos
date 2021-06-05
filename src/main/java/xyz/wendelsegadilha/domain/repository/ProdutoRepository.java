package xyz.wendelsegadilha.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.wendelsegadilha.domain.entity.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
}
