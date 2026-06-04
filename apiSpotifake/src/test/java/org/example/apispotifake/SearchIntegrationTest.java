package org.example.apispotifake;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.apispotifake.dto.UsuarioDTO;
import org.example.apispotifake.model.Album;
import org.example.apispotifake.model.Artista;
import org.example.apispotifake.model.Track;
import org.example.apispotifake.model.Usuario;
import org.example.apispotifake.repository.AlbumRepository;
import org.example.apispotifake.repository.ArtistaRepository;
import org.example.apispotifake.repository.TrackRepository;
import org.example.apispotifake.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.util.Map;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.hasSize;

@SpringBootTest
public class SearchIntegrationTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TrackRepository trackRepository;

    @Autowired
    private ArtistaRepository artistaRepository;

    @Autowired
    private AlbumRepository albumRepository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private ObjectMapper objectMapper = new ObjectMapper();

    private String token;

    @BeforeEach
    void setup() throws Exception {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        trackRepository.deleteAll();
        albumRepository.deleteAll();
        artistaRepository.deleteAll();
        usuarioRepository.deleteAll();

        Usuario user = new Usuario();
        user.setUsuario("search_user");
        user.setPassword(passwordEncoder.encode("pass"));
        usuarioRepository.save(user);

        UsuarioDTO loginDTO = new UsuarioDTO();
        loginDTO.setUsuario("search_user");
        loginDTO.setPassword("pass");

        MvcResult result = mockMvc.perform(post("/usuarios/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginDTO)))
                .andExpect(status().isOk())
                .andReturn();

        Map map = objectMapper.readValue(result.getResponse().getContentAsString(), Map.class);
        token = (String) map.get("token");

        // Seed
        Artista a1 = new Artista(); a1.setNombre("Linkin Park"); artistaRepository.save(a1);
        Artista a2 = new Artista(); a2.setNombre("Daft Punk"); artistaRepository.save(a2);

        Album alb1 = new Album(); alb1.setTitulo("Meteora"); alb1.setArtista(a1); albumRepository.save(alb1);
        Album alb2 = new Album(); alb2.setTitulo("Discovery"); alb2.setArtista(a2); albumRepository.save(alb2);

        Track t1 = new Track(); t1.setTitulo("Numb"); t1.setArtista(a1); t1.setAlbum(alb1); trackRepository.save(t1);
        Track t2 = new Track(); t2.setTitulo("One More Time"); t2.setArtista(a2); t2.setAlbum(alb2); trackRepository.save(t2);
    }

    @Test
    void testBuscarPorTitulo() throws Exception {
        mockMvc.perform(get("/actividad/buscar")
                .param("query", "Numb")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].titulo").value("Numb"));
    }

    @Test
    void testBuscarPorArtista() throws Exception {
        mockMvc.perform(get("/actividad/buscar")
                .param("query", "Daft")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].artistaNombre").value("Daft Punk"));
    }

    @Test
    void testBuscarSinResultados() throws Exception {
        mockMvc.perform(get("/actividad/buscar")
                .param("query", "Inexistente")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }
}
