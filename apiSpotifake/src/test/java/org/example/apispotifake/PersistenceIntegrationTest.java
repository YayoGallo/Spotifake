package org.example.apispotifake;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.apispotifake.dto.UsuarioDTO;
import org.example.apispotifake.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class PersistenceIntegrationTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private UsuarioRepository usuarioRepository;

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
    void testFlujoCompletoPersistenciaH2() throws Exception {
        // 1. CREATE (Add)
        UsuarioDTO user = new UsuarioDTO();
        user.setUsuario("crud_user");
        user.setPassword("password");
        user.setNombre("Original");
        user.setEmail("crud@test.com");

        mockMvc.perform(post("/usuarios/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk());

        // 2. READ & VERIFY
        assertNotNull(usuarioRepository.findByUsuario("crud_user"), "El usuario debería existir en H2 tras el add");

        // 3. UPDATE (Edit)
        user.setNombre("Modificado");
        mockMvc.perform(post("/usuarios/edit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk());

        assertEquals("Modificado", usuarioRepository.findByUsuario("crud_user").getNombre(), "El cambio debería persistir en H2");

        // 4. DELETE
        mockMvc.perform(post("/usuarios/delete")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk());

        assertNull(usuarioRepository.findByUsuario("crud_user"), "El usuario no debería existir tras el delete");
    }
}
