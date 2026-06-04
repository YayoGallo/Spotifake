# Caso de Uso: Pantalla de Inicio (Home) - Spotifake

## Descripción
La pantalla principal de Spotifake donde el usuario descubre contenido relevante y accede rápidamente a sus escuchas recientes.

## Actores
- Usuario autenticado.

## Flujos de Trabajo
1. **Recomendaciones Personalizadas:**
   - El cliente solicita recomendaciones al `Servicio de Actividad`.
   - El backend procesa el historial y devuelve una lista de tracks/playlists.
   - La UI renderiza tarjetas con imágenes de alta resolución.
2. **Reproducción Reciente:**
   - Carga desde la base de datos local (Room) o caché las últimas pistas reproducidas para acceso offline rápido.
3. **Novedades:**
   - Muestra los lanzamientos más recientes basados en las suscripciones del usuario a artistas.

## Requerimientos Técnicos (Arquitectura)
- **ViewModel:** Gestiona el estado de carga (`Loading`), éxito (`Success`) y error (`Error`).
- **Data Layer:** Repositorio que combina datos de API remota y caché local.
- **Componentes Compose:** `LazyRow` para carruseles horizontales de álbumes.
