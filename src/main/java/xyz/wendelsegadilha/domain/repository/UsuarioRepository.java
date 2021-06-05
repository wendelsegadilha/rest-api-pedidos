package xyz.wendelsegadilha.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.wendelsegadilha.domain.entity.Usuario;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findByLogin(String login);
}
