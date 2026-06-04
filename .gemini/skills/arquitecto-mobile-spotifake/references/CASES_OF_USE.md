# Casos de Uso y Procesos - Spotifake

## Frontend (Kotlin Nativo - Android)

### Pantalla de Inicio (Home)
- **Carga de recomendaciones:** Consumo del servicio de recomendaciones (Spring Boot) para mostrar contenido personalizado.
- **Reproducción Reciente:** Historial de pistas escuchadas por el usuario.
- **Novedades:** Carrusel con los últimos lanzamientos de artistas seguidos.

### Búsqueda (Search)
- **Exploración por Categorías:** Navegación por géneros (Pop, Rock, Indie) y estados de ánimo (Focus, Workout).
- **Búsqueda Dinámica:** Filtrado en tiempo real (Search-as-you-type) de canciones, artistas y álbumes.

### Reproductor (Player)
- **Control Multimedia:** Play, Pause, Siguiente, Anterior, Seek (barra de progreso).
- **Gestión de Cola:** Añadir/eliminar canciones de la cola actual.
- **Modos de Reproducción:** Shuffle (aleatorio) y Repeat (una o todas).
- **Letras Sincronizadas:** Visualización de letras con timing preciso si el metadato está disponible.

### Biblioteca (Library)
- **Me Gusta:** Lista de canciones marcadas con corazón por el usuario.
- **Playlists Propias:** Creación, edición y eliminación de listas de reproducción personales.
- **Modo Offline:** Gestión de descargas locales usando `WorkManager` para ahorro de datos y escucha sin conexión.

### Detalle de Playlist/Álbum
- **Listado de Canciones:** Tabla/Lista con metadatos (Duración, Artista, Álbum).
- **Acciones Rápidas:** Compartir enlace, descargar todo el álbum, seguir artista.

---

## Backend (Spring Boot)

### Servicio de Autenticación
- **Gestión de Sesiones:** Emisión y validación de tokens JWT.
- **Perfiles:** Registro, login y gestión de datos de usuario.

### Servicio de Catálogo
- **API REST:** Endpoints para consultas de metadatos almacenados en bases de datos (Spring Data JPA/Mongo).
- **Búsqueda Indexada:** Implementación de búsqueda eficiente sobre el catálogo de audio.

### Streaming & Entrega
- **Servicio de Archivos:** Generación de URLs prefirmadas (AWS S3 / Google Cloud Storage) para acceso seguro a los binarios de audio (.mp3, .flac).
- **CDN Integration:** Configuración de cacheo para entrega de baja latencia.

### Servicio de Actividad del Usuario
- **Telemetría:** Registro de eventos de escucha, skips y tiempo de reproducción para alimentar el motor de recomendaciones.
- **Estadísticas:** Generación de resúmenes anuales/mensuales de escucha.

### Sincronización de Datos
- **Consistencia:** Mantener playlists y bibliotecas sincronizadas en tiempo real entre múltiples dispositivos del mismo usuario.
