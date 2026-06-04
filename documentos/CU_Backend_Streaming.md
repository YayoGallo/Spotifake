# Caso de Uso: Catálogo y Streaming (Backend) - Spotifake

## Descripción
Gestión de metadatos musicales y entrega segura de archivos de audio.

## Actores
- Usuario, Sistema de Streaming.

## Flujos de Trabajo
1. **Consulta de Catálogo:**
   - Búsqueda y filtrado de canciones/artistas mediante JPA.
2. **Generación de URL de Streaming:**
   - El backend genera una URL temporal/prefirmada para que el cliente Android descargue el chunk de audio.

## Requerimientos Técnicos
- **PostgreSQL:** Para metadatos relacionales.
- **S3/Cloud Storage Integration:** Para el almacenamiento de archivos binarios pesados.
- **Cache (Redis):** Para resultados de búsqueda frecuentes.
