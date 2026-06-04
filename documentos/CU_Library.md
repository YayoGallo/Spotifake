# Caso de Uso: Biblioteca y Offline - Spotifake

## Descripción
Gestión de contenido personal y descarga de música para escucha sin internet.

## Actores
- Usuario autenticado (Premium para Offline).

## Flujos de Trabajo
1. **Playlists Personales:**
   - Crear, editar nombre/carátula y añadir canciones.
2. **Me Gusta:**
   - Toggle rápido (corazón) que sincroniza con el backend.
3. **Modo Offline:**
   - El usuario marca un álbum/playlist para descargar.
   - `WorkManager` gestiona la descarga de archivos binarios (.mp3) a almacenamiento seguro.

## Requerimientos Técnicos
- **WorkManager:** Para descargas en background persistentes.
- **Room Persistence:** Base de datos para metadatos locales.
- **Seguridad:** Archivos de audio encriptados o almacenados en directorio privado de la app.
