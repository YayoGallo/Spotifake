package org.example.apispotifake.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.apispotifake.dto.UsuarioDTO;
import org.example.apispotifake.model.Usuario;
import org.example.apispotifake.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class IUsuarioServiceImpl implements IUsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    private final ObjectMapper objectMapper = new ObjectMapper()
            .configure(com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public UsuarioDTO findByUsername(String username) {
        Usuario usuario = usuarioRepository.findByUsuario(username);
        return (usuario != null) ? objectMapper.convertValue(usuario, UsuarioDTO.class) : null;
    }

    @Override
    public UsuarioDTO addUsuario(UsuarioDTO usuarioDTO) {
        Usuario usuarioDB = objectMapper.convertValue(usuarioDTO, Usuario.class);
        usuarioDB.setPassword(passwordEncoder.encode(usuarioDTO.getPassword()));
        usuarioDB = usuarioRepository.save(usuarioDB);
        return objectMapper.convertValue(usuarioDB, UsuarioDTO.class);
    }

    @Override
    public UsuarioDTO updateUsuario(UsuarioDTO usuarioDTO) {
        Usuario usuarioDB = usuarioRepository.findByUsuario(usuarioDTO.getUsuario());
        if (usuarioDB != null) {
            if (usuarioDTO.getNombre() != null) usuarioDB.setNombre(usuarioDTO.getNombre());
            if (usuarioDTO.getApellido() != null) usuarioDB.setApellido(usuarioDTO.getApellido());
            if (usuarioDTO.getEmail() != null) usuarioDB.setEmail(usuarioDTO.getEmail());
            if (usuarioDTO.getPassword() != null && !usuarioDTO.getPassword().isEmpty()) {
                usuarioDB.setPassword(passwordEncoder.encode(usuarioDTO.getPassword()));
            }
            usuarioDB = usuarioRepository.save(usuarioDB);
            return objectMapper.convertValue(usuarioDB, UsuarioDTO.class);
        }
        return null;
    }

    @Override
    public UsuarioDTO login(String username, String password) {
        Usuario usuario = usuarioRepository.findByUsuario(username);
        if (usuario != null && passwordEncoder.matches(password, usuario.getPassword())) {
            return objectMapper.convertValue(usuario, UsuarioDTO.class);
        }
        return null;
    }

    @Override
    public java.util.List<UsuarioDTO> getAllUsuarios() {
        java.util.List<UsuarioDTO> result = new java.util.ArrayList<>();
        usuarioRepository.findAll().forEach(u -> result.add(objectMapper.convertValue(u, UsuarioDTO.class)));
        return result;
    }

    @Override
    public void deleteUsuario(String username) {
        Usuario usuario = usuarioRepository.findByUsuario(username);
        if (usuario != null) {
            usuarioRepository.delete(usuario);
        }
    }
}
