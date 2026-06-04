Requerimientos Funcionales y Técnicos: Proyecto Udelp Music
Este documento detalla cada punto específico que debe cumplir el proyecto para considerarse completo, basado en los Casos de Uso (CU) y la arquitectura definida.

1. Módulo de Backend (Spring Boot)
El servidor debe exponer una API REST robusta con las siguientes capacidades:

A. Seguridad y Acceso (CU-B04)
[ ] Registro de Usuarios: Endpoint para crear nuevas cuentas con contraseñas encriptadas.
[ ] Autenticación JWT: Generación de un token válido tras un login exitoso.
[ ] Validación de Token: Filtro de seguridad que valide el token en cada petición a recursos protegidos.
B. Streaming y Contenido (CU-B01)
[ ] Streaming de Audio: Endpoint que entregue el flujo de audio de forma fragmentada (no descarga total inmediata).
[ ] Catálogo de Música: Endpoints para listar canciones, artistas y álbumes.
[ ] Búsqueda: Capacidad de filtrar canciones por nombre o artista desde la base de datos.
C. Estándares Técnicos Backend
[ ] Manejo de Errores: Respuestas JSON consistentes para errores (401 Unauthorized, 404 Not Found, etc.).
[ ] Persistencia: Uso de Spring Data JPA con una base de datos funcional.
2. Módulo de Frontend (Android Kotlin)
La aplicación móvil debe ser intuitiva y conectarse eficientemente al backend.

A. Gestión de Sesión
[ ] Pantalla de Login: Interfaz para ingresar credenciales.
[ ] Pantalla de Registro: Interfaz para crear cuenta.
[ ] Persistencia de Token: El token debe guardarse localmente para evitar loguearse cada vez que se abra la app.
B. Exploración (UI)
[ ] Pantalla Principal (Home): Listado dinámico de canciones obtenidas del backend.
[ ] Buscador: Barra de búsqueda funcional que filtre la lista de canciones.
C. Reproductor Multimedia (CU-F07)
[ ] Controles Básicos: Botones de Play, Pause, Anterior y Siguiente.
[ ] Barra de Progreso (SeekBar): Debe mostrar el avance de la canción y permitir saltar a un tiempo específico.
[ ] Foreground Service: La música NO debe detenerse si el usuario sale de la aplicación o bloquea el teléfono.
[ ] Notificación de Reproducción: Control de música directamente desde la cortina de notificaciones de Android.
3. Requerimientos de Arquitectura (Obligatorios)
No basta con que "funcione", debe seguir estos estándares de ingeniería:

[ ] Arquitectura MVVM: Separación clara entre Activity/Fragment, ViewModel, Repository y Models.
[ ] Zero Hardcoding: Las URLs del servidor y configuraciones deben estar en archivos strings.xml, gradle.properties o similares.
[ ] Manejo de Estados (Livedata/StateFlow): La UI debe reaccionar a los cambios de estado del ViewModel (cargando, éxito, error).
[ ] Inyección de Dependencias: Uso de una herramienta (Hilt o Koin) para proveer servicios y repositorios.
[ ] Internacionalización (i18n): La app debe estar disponible en Español e Inglés, detectando el idioma del sistema.
4. Documentación y Entrega
[ ] README.md: Instrucciones claras para levantar el backend y configurar la app móvil.
[ ] Script de Base de Datos: Archivo .sql con datos de prueba (canciones y usuarios iniciales).
[ ] Capturas de Pantalla / Video: Evidencia del funcionamiento del flujo principal.

