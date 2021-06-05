package xyz.wendelsegadilha.rest.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import xyz.wendelsegadilha.domain.entity.Usuario;
import xyz.wendelsegadilha.exception.SenhaInvalidaException;
import xyz.wendelsegadilha.rest.dto.CredenciaisDTO;
import xyz.wendelsegadilha.rest.dto.TokenDTO;
import xyz.wendelsegadilha.security.jwt.JwtService;
import xyz.wendelsegadilha.service.impl.UsuarioServiceImpl;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioServiceImpl usuarioService;
    private final JwtService jwtService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Salva um Usuário")
    public Usuario save(@RequestBody @Valid Usuario usuario){
        return usuarioService.save(usuario);
    }

    @PostMapping("/auth")
    @ApiOperation("Autentica um Usuário")
    public TokenDTO autenticar(@RequestBody CredenciaisDTO credenciais){
        try {
            Usuario usuario = Usuario.builder().login(credenciais.getLogin()).senha(credenciais.getSenha()).build();
            UserDetails usuarioAutenticado = usuarioService.autenticar(usuario);
            String token = jwtService.gerarToken(usuario);
            return new TokenDTO(usuarioAutenticado.getUsername(), token);
        }catch (UsernameNotFoundException | SenhaInvalidaException e){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

}
