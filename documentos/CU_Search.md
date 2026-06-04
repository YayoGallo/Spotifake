# Caso de Uso: Búsqueda (Search) - Spotifake

## Descripción
Permite a los usuarios encontrar música por texto o explorar categorías predefinidas.

## Actores
- Usuario (autenticado o invitado según configuración).

## Flujos de Trabajo
1. **Exploración por Categorías:**
   - El usuario selecciona un género (Pop, Rock, etc.).
   - La app muestra playlists y álbumes destacados de esa categoría.
2. **Búsqueda Dinámica:**
   - A medida que el usuario escribe, se envían peticiones al `Servicio de Catálogo`.
   - Se muestran resultados divididos en: Canciones, Artistas, Álbumes y Playlists.

## Requerimientos Técnicos
- **Debouncing:** Implementar un retraso (aprox. 300ms) en la búsqueda para evitar peticiones excesivas a la API.
- **Transiciones:** Animación fluida al abrir la barra de búsqueda.
- **Backend:** Búsqueda indexada en PostgreSQL o MongoDB.
