# Modelo Físico y Tipos de Datos - Spotifake

Mapeo de tipos de datos para la implementación en **Spring Data JPA** y **Android Room**.

## 1. Tabla: `users`
| Campo | Tipo JPA (Java) | Tipo Room (Kotlin) | Notas |
| :--- | :--- | :--- | :--- |
| id | Long | Long | Primary Key |
| username | String | String | Unique, Not Null |
| password | String | - | No se guarda en local |
| email | String | String | Unique |
| role | Enum/String | String | USER, PREMIUM, ADMIN |

## 2. Tabla: `tracks`
| Campo | Tipo JPA (Java) | Tipo Room (Kotlin) | Notas |
| :--- | :--- | :--- | :--- |
| id | Long | Long | Primary Key |
| title | String | String | |
| duration | Integer | Int | En segundos |
| stream_url | String | String | URL temporal (S3) |
| album_id | Long | Long | Foreign Key |
| is_offline | - | Boolean | Solo en Room |
| local_path | - | String | Solo en Room |

## 3. Tabla: `playlists`
| Campo | Tipo JPA (Java) | Tipo Room (Kotlin) | Notas |
| :--- | :--- | :--- | :--- |
| id | Long | Long | |
| name | String | String | |
| owner_id | Long | Long | |
| is_public | Boolean | - | Solo Backend |

## 4. Tabla Intermedia: `playlist_tracks`
| Campo | Tipo JPA | Notas |
| :--- | :--- | :--- |
| playlist_id | Long | FK |
| track_id | Long | FK |
| position | Integer | Orden en la lista |

---

## Estrategia de Sincronización
- **Backend:** PostgreSQL para integridad referencial.
- **Frontend:** Room actuará como una "Single Source of Truth" para la UI mediante `Flow<List<Track>>`.
- **API:** Los DTOs (Data Transfer Objects) omitirán campos sensibles como contraseñas o URLs de streaming expiradas.
