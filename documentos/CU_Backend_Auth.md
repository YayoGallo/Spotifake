# Caso de Uso: Autenticación y Seguridad (Backend) - Spotifake

## Descripción
Proceso de identificación de usuarios y protección de recursos mediante JWT.

## Actores
- Usuario, Administrador.

## Flujos de Trabajo
1. **Login:**
   - Usuario envía credenciales.
   - Backend valida y devuelve un JWT con roles y tiempo de expiración.
2. **Validación de Token:**
   - Interceptor en Spring Security valida el token en cada petición a recursos protegidos.

## Requerimientos Técnicos
- **Spring Security + JWT.**
- **Bcrypt:** Para el hasheo de contraseñas.
- **Refresh Tokens:** Para mantener la sesión activa sin logueos constantes.
