# Estándares de Desarrollo Backend - Spotifake (Spring Boot)

## Arquitectura del Servicio
- **Controladores (Controllers):** Definición clara de endpoints RESTful. Validación de inputs con `@Valid`.
- **Servicios (Services):** Lógica de negocio pura. Inyección de dependencias por constructor.
- **Repositorios (Repositories):** Uso de Spring Data JPA (PostgreSQL) o Spring Data MongoDB.
- **DTOs (Data Transfer Objects):** Para separar las entidades de base de datos de los datos expuestos en la API.
- **Mappers:** Uso de MapStruct o manual para transformar entre Entidad y DTO.

## Seguridad y Autenticación
- **JWT (JSON Web Tokens):** Emisión de tokens tras login exitoso. Filtros de seguridad para validar el token en cada petición.
- **Roles:** Soporte inicial para roles `USER` y `ADMIN` (si aplica para gestión de catálogo).

## Manejo de Excepciones
- **GlobalExceptionHandler:** Uso de `@ControllerAdvice` para devolver errores estructurados (Código de error, mensaje legible, timestamp).

## Almacenamiento de Archivos (Streaming)
- **AWS S3 / Google Cloud Storage:** Los binarios (.mp3, .flac) no se guardan en la base de datos, solo sus metadatos y la ruta de acceso.
- **Firmado de URLs:** Generación de URLs temporales para evitar acceso no autorizado directo al bucket.

## Testing
- **JUnit 5 & Mockito:** Tests unitarios obligatorios para servicios críticos.
- **Testcontainers:** Para pruebas de integración reales con bases de datos.
