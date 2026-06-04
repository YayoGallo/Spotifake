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
public class ActividadIntegrationTest {

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

        // Limpiar base de datos
        trackRepository.deleteAll();
        albumRepository.deleteAll();
        artistaRepository.deleteAll();
        usuarioRepository.deleteAll();

        // Crear usuario de prueba
        Usuario user = new Usuario();
        user.setUsuario("qa_user");
        user.setPassword(passwordEncoder.encode("qa_pass"));
        user.setEmail("qa@spotifake.com");
        usuarioRepository.save(user);

        // Obtener token
        UsuarioDTO loginDTO = new UsuarioDTO();
        loginDTO.setUsuario("qa_user");
        loginDTO.setPassword("qa_pass");

        MvcResult result = mockMvc.perform(post("/usuarios/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginDTO)))
                .andExpect(status().isOk())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        Map<String, Object> map = objectMapper.readValue(response, Map.class);
        token = (String) map.get("token");

        // Seed data for recommendations
        Artista artista = new Artista();
        artista.setNombre("Test Artist");
        artista = artistaRepository.save(artista);

        Album album = new Album();
        album.setTitulo("Test Album");
        album.setArtista(artista);
        album.setFechaLanzamiento(LocalDate.now());
        album.setPortadaUrl("http://example.com/cover.jpg");
        album = albumRepository.save(album);

        Track track = new Track();
        track.setTitulo("Test Track");
        track.setArtista(artista);
        track.setAlbum(album);
        track.setStreamUrl("http://example.com/stream.mp3");
        trackRepository.save(track);
    }

    @Test
    void testGetRecomendacionesConToken() throws Exception {
        mockMvc.perform(get("/actividad/recomendaciones")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].titulo").value("Test Track"))
                .andExpect(jsonPath("$[0].artistaNombre").value("Test Artist"));
    }

    @Test
    void testGetRecomendacionesSinToken() throws Exception {
        mockMvc.perform(get("/actividad/recomendaciones"))
                .andExpect(status().isForbidden());
    }
}
