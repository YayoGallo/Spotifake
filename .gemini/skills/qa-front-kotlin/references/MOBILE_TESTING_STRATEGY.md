# Estrategia de QA Frontend (Android) - Spotifake

## Niveles de Prueba Móvil

### 1. Pruebas Unitarias de Lógica de UI
- **Herramientas:** JUnit 5, MockK.
- **Objetivo:** Validar los ViewModels y la lógica de estado sin necesidad de un emulador.

### 2. Pruebas de Componentes (Compose Tests)
- **Herramientas:** Compose Testing Library.
- **Objetivo:** Verificar que los composables se renderizan correctamente y responden a las interacciones (clics, texto, etc.).

### 3. Pruebas de Integración y UI (Instrumented Tests)
- **Herramientas:** Espresso, Barista.
- **Objetivo:** Ejecutar flujos completos de usuario en un emulador o dispositivo real (ej: Login -> Search -> Play Song).

### 4. Pruebas de Snapshot / Regresión Visual
- **Objetivo:** Detectar cambios inesperados en el diseño o pixel-perfect issues al actualizar componentes.

### 5. Pruebas de Performance en Dispositivo
- **Herramientas:** Macrobenchmark.
- **Objetivo:** Medir tiempos de arranque (Startup) y fluidez de scroll (Jank) para garantizar los 60fps.

## Áreas Críticas a Validar
- **Reproductor:** Verificar que los controles de medios responden correctamente bajo diferentes estados de red.
- **Offline Mode:** Asegurar que la UI maneja correctamente la falta de conexión.
- **Adaptabilidad:** Probar en diferentes tamaños de pantalla (móvil, tablet) y orientaciones.
- **Accesibilidad:** Uso de TalkBack y validación de contrastes.

## Checklist de QA Mobile
- [ ] ¿El componente maneja el estado de "Cargando"?
- [ ] ¿Se muestra un mensaje de error amigable si falla la API?
- [ ] ¿Los botones tienen un área táctil mínima de 48dp?
- [ ] ¿Se han añadido `contentDescription` a las imágenes y botones iconográficos?
