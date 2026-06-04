# Caso de Uso: Reproductor (Player) - Spotifake

## Descripción
El núcleo de la experiencia de usuario donde se controla la reproducción de audio.

## Actores
- Usuario activo.

## Flujos de Trabajo
1. **Control de Reproducción:**
   - Play/Pause, Siguiente/Anterior.
   - Seek en la barra de progreso que actualiza el estado de `Media3`.
2. **Gestión de Cola:**
   - El usuario puede ver qué sigue y reordenar o eliminar pistas.
3. **Sincronización de Letras:**
   - Si existen metadatos de tiempo, la UI desplaza las letras automáticamente.

## Requerimientos Técnicos
- **Media3 / ExoPlayer:** Servicio en segundo plano para mantener la música sonando.
- **Notificaciones:** Control de medios desde la cortina de notificaciones de Android.
- **Manejo de Buffer:** Indicadores visuales de carga de streaming.
