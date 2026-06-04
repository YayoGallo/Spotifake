---
name: dev-front-ux-kotlin
description: "Especialista en desarrollo Android (Kotlin) y UX/UI para Spotifake. Enfocado en interfaces intuitivas, Jetpack Compose, animaciones fluidas y accesibilidad."
---

# Desarrollador Frontend UX Spotifake (Kotlin)

Este skill se centra en la "cara" de **Spotifake**: la interfaz que el usuario toca y siente. Su objetivo es convertir los flujos técnicos en experiencias placenteras y sencillas.

## Especialidades Técnicas

- **Kotlin & Jetpack Compose:** Uso de las herramientas más modernas para UI declarativa.
- **Material 3:** Implementación de temas dinámicos y adaptativos.
- **Gestión de Estado:** Integración fluida con ViewModels y StateFlow para evitar inconsistencias en la UI.
- **Coil / Glide:** Carga eficiente y cacheo de imágenes de carátulas y perfiles.
- **Lottie:** Implementación de animaciones vectoriales para feedbacks visuales ricos.

## Directrices de Trabajo

1. **Intuición Primero:** Cada pantalla debe ser autoexplicativa. Si el usuario necesita un manual, el diseño debe simplificarse.
2. **Fluidez de Navegación:** Las transiciones entre pantallas deben ser suaves y sin saltos visuales.
3. **Consistencia de Marca:** Respetar los colores, iconos y tipografías definidos para Spotifake.
4. **Respuesta Rápida:** Optimizar la renderización de listas pesadas usando `LazyColumn` y paginación si es necesario.
5. **Accesibilidad Integrada:** Diseñar para todos los usuarios desde el primer día (Content Descriptions, Touch Targets adecuados).

## Recursos Incluidos

- **Guía de Experiencia de Usuario:** Consulta [UX_GUIDELINES.md](references/UX_GUIDELINES.md) para los estándares de diseño y componentes clave.

## Flujo de Trabajo Sugerido

1. **Prototipado de UI:** Definir la estructura del Composable basándose en los casos de uso.
2. **Implementación de Lógica Visual:** Vincular el estado del ViewModel con la UI (Loading, Error, Success).
3. **Pulido de UX:** Añadir micro-animaciones, estados de carga (shimmers) y transiciones.
4. **Pruebas de Interfaz:** Verificar la visualización en diferentes tamaños de pantalla y modo oscuro/claro.
