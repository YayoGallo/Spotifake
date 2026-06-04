# Estrategia de QA Backend - Spotifake

## Niveles de Prueba

### 1. Pruebas Unitarias (Unit Testing)
- **Herramientas:** JUnit 5, Mockito.
- **Objetivo:** Validar la lógica de negocio en los servicios de forma aislada.
- **Cobertura Mínima:** 80% de las clases de servicio.

### 2. Pruebas de Integración (Integration Testing)
- **Herramientas:** Testcontainers, Spring Boot Test.
- **Objetivo:** Verificar la interacción entre el código y la base de datos (PostgreSQL/MongoDB) o servicios externos.
- **Escenarios:** Persistencia de datos, transacciones y consultas complejas.

### 3. Pruebas de API (Contract & Functional Testing)
- **Herramientas:** RestAssured o MockMvc.
- **Objetivo:** Asegurar que los endpoints devuelven los códigos de estado HTTP correctos y el cuerpo de respuesta esperado (JSON).
- **Validación de Contrato:** Comprobar que los DTOs coinciden con lo acordado con el equipo de Frontend.

### 4. Pruebas de Seguridad
- **JWT Validation:** Verificar que los endpoints protegidos rechazan peticiones sin token o con tokens expirados/inválidos.
- **Role-Based Access Control (RBAC):** Asegurar que solo usuarios con rol `ADMIN` pueden realizar acciones críticas.

### 5. Pruebas de Carga y Rendimiento
- **Objetivo:** Validar la latencia en la entrega de URLs de streaming y metadatos bajo concurrencia.

## Checklist de Calidad para Pull Requests (PR)
- [ ] ¿Tiene pruebas unitarias para la nueva lógica?
- [ ] ¿Los nombres de los tests son descriptivos (Ej: `shouldReturnProductListWhenDatabaseIsNotEmpty`)?
- [ ] ¿Se han manejado los escenarios de error (404, 400, 500)?
- [ ] ¿La documentación de Swagger/OpenAPI está actualizada?
