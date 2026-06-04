# Spotifake 

Spotifake es un reproductor de música nativo para Android que se conecta a un backend robusto en Spring Boot.

## Estructura del Proyecto

- **apiSpotifake:** Servidor Backend construido con Spring Boot 3.x.
- **Ejemplo:** Aplicación Android nativa construida con Kotlin y Jetpack Compose.
- **documentos:** Documentación técnica, casos de uso y diagramas de arquitectura.

## Requisitos Previos

- **Java 17** o superior.
- **Android Studio** (Ladybug o superior recomendado).
- **Maven** (incluido mediante `./mvnw`).

## Configuración del Backend (apiSpotifake)

1. Navega a la carpeta `apiSpotifake`.
2. Ejecuta el comando para levantar el servidor:
   ```bash
   ./mvnw spring-boot:run
   ```
3. El servidor estará disponible en `http://localhost:8080`.
4. La consola de H2 está habilitada en `http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:mem:spotifakedb`).
5. Se cargan datos de prueba automáticamente mediante `src/main/resources/data.sql`.

## Configuración de la App Móvil (Ejemplo)

1. Abre la carpeta `Ejemplo` en Android Studio.
2. Asegúrate de que el emulador tenga acceso a `10.0.2.2:8080` (configurado por defecto en `strings.xml`).
3. Sincroniza el proyecto con Gradle.
4. Ejecuta la aplicación en un emulador o dispositivo físico.

## Características Implementadas

- **Autenticación:** Registro y login con JWT.
- **Streaming Nativo:** Endpoint en el backend que sirve archivos de audio locales.
- **Reproducción en Segundo Plano:** Uso de Media3 `MediaSessionService` para mantener la música activa fuera de la app.
- **Arquitectura:** MVVM, Inyección de dependencias con Hilt y Clean Architecture.
- **Internacionalización:** Soporte para Español e Inglés.
