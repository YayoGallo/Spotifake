package org.example.apispotifake.repository;

import org.example.apispotifake.model.Usuario;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, Integer> {

    public Usuario findByUsuario(String usuario);

    Optional<Usuario> findByUsuarioAndPassword(String usuario, String password);
}
