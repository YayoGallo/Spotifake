package org.example.apispotifake.service;

import org.example.apispotifake.dto.UsuarioDTO;
import java.util.List;

public interface IUsuarioService {

    UsuarioDTO findByUsername(String username);
    UsuarioDTO addUsuario(UsuarioDTO usuario);
    UsuarioDTO updateUsuario(UsuarioDTO usuario);
    UsuarioDTO login(String username, String password);
    List<UsuarioDTO> getAllUsuarios();
    void deleteUsuario(String username);

}
