# Diagrama Entidad-Relación (ER) - Spotifake

Utilizamos la notación de Mermaid para representar las relaciones entre las entidades del sistema.

```mermaid
erDiagram
    USER ||--o{ PLAYLIST : creates
    USER ||--o{ USER_ACTIVITY : generates
    USER ||--o{ USER_LIKES : marks
    
    ARTIST ||--o{ ALBUM : releases
    ARTIST ||--o{ TRACK : performs
    
    ALBUM ||--|{ TRACK : contains
    
    PLAYLIST }|--|{ TRACK : includes
    
    TRACK }|--|| GENRE : categorized_by
    
    USER_LIKES }|--|| TRACK : references
    USER_ACTIVITY }|--|| TRACK : references

    USER {
        long id PK
        string username UK
        string password_hash
        string email UK
        string role
        string avatar_url
    }

    TRACK {
        long id PK
        string title
        int duration_sec
        string stream_url
        long album_id FK
        long artist_id FK
        long genre_id FK
    }

    ALBUM {
        long id PK
        string title
        date release_date
        string cover_url
        long artist_id FK
    }

    ARTIST {
        long id PK
        string name
        text bio
        string photo_url
    }

    PLAYLIST {
        long id PK
        string name
        boolean is_public
        long owner_id FK
    }
```

## Relaciones Críticas
1. **User - Playlist (1:N):** Un usuario puede ser dueño de muchas listas.
2. **Playlist - Track (M:N):** Una canción puede estar en muchas listas y una lista tiene muchas canciones.
3. **Artist - Album (1:N):** Un álbum pertenece a un solo artista principal (simplificación inicial).
4. **UserActivity (1:N):** Registro histórico para el "Feed" de la Home.
