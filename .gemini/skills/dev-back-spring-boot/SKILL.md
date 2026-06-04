---
name: dev-back-spring-boot
description: "Desarrollador experto en backend con Spring Boot para el proyecto Spotifake. Especialista en APIs RESTful, seguridad JWT, microservicios y streaming de audio."
---

# Desarrollador Backend Spotifake (Spring Boot)

Este skill proporciona la base técnica para construir, mantener y escalar los servicios del servidor de **Spotifake**. Se enfoca en la robustez de las APIs y la seguridad de los datos de streaming.

## Tecnologías y Herramientas

- **Java 17+ / Kotlin:** Lenguaje principal para el servidor.
- **Spring Boot 3.x:** Framework base.
- **Spring Security:** Con soporte para **JWT** y filtrado de peticiones.
- **Spring Data JPA:** Gestión de persistencia de datos relacionales utilizando **H2 Database** por defecto para garantizar entornos de prueba rápidos.
- **AWS SDK:** Para la integración con servicios de almacenamiento como S3.
- **Docker:** Contenedores para el despliegue de los microservicios.

## Directrices de Desarrollo

1. **REST APIs:** Diseñar endpoints claros y semánticos siguiendo los estándares HTTP.
2. **Seguridad Nativa:** Implementar protección de rutas por defecto y validar tokens en cada petición.
3. **Escalabilidad:** Diseñar servicios sin estado (Stateless) para facilitar el escalado horizontal.
4. **Eficiencia en Streaming:** Optimizar la entrega de metadatos de audio y URLs seguras para el frontend.
5. **Testing de Calidad:** Implementar tests de integración con bases de datos reales usando Testcontainers.

## Recursos Incluidos

- **Estándares Técnicos:** Consulta [STANDARDS.md](references/STANDARDS.md) para detalles sobre arquitectura y patrones.

## Flujo de Trabajo Sugerido

1. **Diseño de API:** Definir los DTOs y la estructura de los JSON que consumirá la app Android.
2. **Implementación de Servicio:** Desarrollar la lógica de negocio y asegurar el manejo correcto de excepciones.
3. **Persistencia:** Configurar las entidades y repositorios de datos.
4. **Validación:** Escribir tests para verificar la corrección del endpoint y su seguridad.
