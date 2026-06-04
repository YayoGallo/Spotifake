package org.example.apispotifake.controller;

import org.example.apispotifake.dto.UsuarioDTO;
import org.example.apispotifake.service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private IUsuarioService usuarioService;

    @Autowired
    private org.example.apispotifake.security.JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<UsuarioDTO> login(@RequestBody UsuarioDTO usuarioDTO) {
        UsuarioDTO usuarioResponse = usuarioService.login(usuarioDTO.getUsuario(), usuarioDTO.getPassword());
        if (usuarioResponse != null) {
            String token = jwtUtils.generateToken(usuarioResponse.getUsuario());
            usuarioResponse.setToken(token);
            return ResponseEntity.ok(usuarioResponse);
        }
        throw new BadCredentialsException("Usuario o contraseña incorrectos.");
    }

    @PostMapping("/add")
    public ResponseEntity<UsuarioDTO> add(@RequestBody UsuarioDTO usuarioDTO) {
        if (usuarioService.findByUsername(usuarioDTO.getUsuario()) != null) {
            throw new RuntimeException("El nombre de usuario '" + usuarioDTO.getUsuario() + "' ya está en uso.");
        }
        UsuarioDTO usuarioResponse = usuarioService.addUsuario(usuarioDTO);
        return ResponseEntity.ok(usuarioResponse);
    }

    @PostMapping("/edit")
    public ResponseEntity<UsuarioDTO> edit(@RequestBody UsuarioDTO usuarioDTO) {
        UsuarioDTO usuarioResponse = usuarioService.updateUsuario(usuarioDTO);
        if (usuarioResponse == null) {
            throw new RuntimeException("No se pudo encontrar al usuario para editar.");
        }
        return ResponseEntity.ok(usuarioResponse);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<UsuarioDTO>> getAll() {
        return ResponseEntity.ok(usuarioService.getAllUsuarios());
    }

    @PostMapping("/delete")
    public ResponseEntity<Void> delete(@RequestBody UsuarioDTO usuarioDTO) {
        usuarioService.deleteUsuario(usuarioDTO.getUsuario());
        return ResponseEntity.ok().build();
    }
}
