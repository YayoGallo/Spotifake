package org.example.apispotifake;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.apispotifake.controller.UsuarioController;
import org.example.apispotifake.dto.UsuarioDTO;
import org.example.apispotifake.model.Usuario;
import org.example.apispotifake.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
public class UsuarioAuthIntegrationTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private ObjectMapper objectMapper = new ObjectMapper()
            .configure(com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
        usuarioRepository.deleteAll();
    }

    @Test
    void testRegistroExitosoYHasheo() throws Exception {
        UsuarioDTO userDTO = new UsuarioDTO();
        userDTO.setUsuario("testuser");
        userDTO.setPassword("password123");
        userDTO.setEmail("test@spotifake.com");

        mockMvc.perform(post("/usuarios/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isOk());

        Usuario userDB = usuarioRepository.findByUsuario("testuser");
        assertNotNull(userDB);
        assertNotEquals("password123", userDB.getPassword());
        assertTrue(passwordEncoder.matches("password123", userDB.getPassword()));
    }

    @Test
    void testLoginExitosoConJWT() throws Exception {
        // Primero registramos un usuario
        Usuario user = new Usuario();
        user.setUsuario("loginuser");
        user.setPassword(passwordEncoder.encode("secret"));
        usuarioRepository.save(user);

        UsuarioDTO loginDTO = new UsuarioDTO();
        loginDTO.setUsuario("loginuser");
        loginDTO.setPassword("secret");

        mockMvc.perform(post("/usuarios/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists())
                .andExpect(jsonPath("$.usuario").value("loginuser"));
    }

    @Test
    void testLoginFallido() throws Exception {
        UsuarioDTO loginDTO = new UsuarioDTO();
        loginDTO.setUsuario("wronguser");
        loginDTO.setPassword("wrongpass");

        mockMvc.perform(post("/usuarios/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginDTO)))
                .andExpect(status().isUnauthorized());
    }
}
