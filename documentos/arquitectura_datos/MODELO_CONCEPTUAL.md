# Modelo Conceptual de Datos - Spotifake

Este documento describe las entidades core y su propósito dentro del ecosistema de Spotifake (Mobile + Backend).

## 1. Núcleo de Contenido (Catálogo)
- **Track (Canción):** El átomo del sistema. Contiene metadatos de audio, duración y referencias al álbum y artista.
- **Album:** Agrupador lógico de Tracks con una portada común y fecha de lanzamiento.
- **Artist:** Entidad que representa al creador del contenido.
- **Genre:** Categorización para búsquedas y recomendaciones.

## 2. Gestión de Usuarios y Seguridad
- **User:** Perfil de usuario, credenciales (hash) y rol (Free/Premium).
- **Session (JWT):** Aunque es volátil, el modelo debe prever la invalidación de tokens y el refresh token.

## 3. Interacción y Personalización (Biblioteca)
- **Playlist:** Colecciones creadas por el usuario o el sistema. Pueden ser públicas o privadas.
- **UserLikes:** Relación binaria (Favoritos) entre un Usuario y un Track/Album.
- **UserActivity (History):** Registro de reproducciones para alimentar el algoritmo de "Home".

## 4. Persistencia Local (Mobile)
- **LocalCache:** Réplica de Tracks y Playlists marcados para "Modo Offline".
- **DownloadQueue:** Estado de las descargas gestionadas por WorkManager.
