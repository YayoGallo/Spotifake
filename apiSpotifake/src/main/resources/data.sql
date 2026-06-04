-- Script de Catálogo Musical - Spotifake
-- Usuarios
INSERT INTO usuarios (usuario, password, email) VALUES ('admin', '$2a$10$8.UnVuG9HHgffUDAlk8q6uy5QLwLpNxtCyYp6n7t789b9b9b9b9b9', 'admin@spotifake.com');

-- Artistas (5)
INSERT INTO artistas (nombre, biografia) VALUES ('Bisqo Dark', 'Especialista en sonidos oscuros y mágicos.');
INSERT INTO artistas (nombre, biografia) VALUES ('Wand Magic', 'Maestro de las varitas y el fuego.');
INSERT INTO artistas (nombre, biografia) VALUES ('Shimmer Corrupt', 'Campanas etéreas con un toque de corrupción.');
INSERT INTO artistas (nombre, biografia) VALUES ('Magevil Ring', 'Hechizos de debilitamiento y anillos de poder.');
INSERT INTO artistas (nombre, biografia) VALUES ('Magspel Quick', 'Velocidad y precisión en cada conjuro.');

-- Albums (3 por artista = 15 total)
-- Bisqo Dark (ID 1)
INSERT INTO albums (titulo, fecha_lanzamiento, portada_url, artista_id) VALUES ('Magic Cast', '2024-01-01', 'https://picsum.photos/400/400?random=11', 1);
INSERT INTO albums (titulo, fecha_lanzamiento, portada_url, artista_id) VALUES ('Deep Impacts', '2024-02-01', 'https://picsum.photos/400/400?random=12', 1);
INSERT INTO albums (titulo, fecha_lanzamiento, portada_url, artista_id) VALUES ('Small Tails', '2024-03-01', 'https://picsum.photos/400/400?random=13', 1);

-- Wand Magic (ID 2)
INSERT INTO albums (titulo, fecha_lanzamiento, portada_url, artista_id) VALUES ('Medium Casts', '2024-01-01', 'https://picsum.photos/400/400?random=21', 2);
INSERT INTO albums (titulo, fecha_lanzamiento, portada_url, artista_id) VALUES ('Short Casts', '2024-02-01', 'https://picsum.photos/400/400?random=22', 2);
INSERT INTO albums (titulo, fecha_lanzamiento, portada_url, artista_id) VALUES ('Fire Spells', '2024-03-01', 'https://picsum.photos/400/400?random=23', 2);

-- Shimmer Corrupt (ID 3)
INSERT INTO albums (titulo, fecha_lanzamiento, portada_url, artista_id) VALUES ('Bell Spells', '2024-01-01', 'https://picsum.photos/400/400?random=31', 3);
INSERT INTO albums (titulo, fecha_lanzamiento, portada_url, artista_id) VALUES ('Corrupted Bells', '2024-02-01', 'https://picsum.photos/400/400?random=32', 3);
INSERT INTO albums (titulo, fecha_lanzamiento, portada_url, artista_id) VALUES ('Ethereal Shimmer', '2024-03-01', 'https://picsum.photos/400/400?random=33', 3);

-- Magevil Ring (ID 4)
INSERT INTO albums (titulo, fecha_lanzamiento, portada_url, artista_id) VALUES ('Debuff Rings', '2024-01-01', 'https://picsum.photos/400/400?random=41', 4);
INSERT INTO albums (titulo, fecha_lanzamiento, portada_url, artista_id) VALUES ('Dark Debuffs', '2024-02-01', 'https://picsum.photos/400/400?random=42', 4);
INSERT INTO albums (titulo, fecha_lanzamiento, portada_url, artista_id) VALUES ('Cursed Magic', '2024-03-01', 'https://picsum.photos/400/400?random=43', 4);

-- Magspel Quick (ID 5)
INSERT INTO albums (titulo, fecha_lanzamiento, portada_url, artista_id) VALUES ('Wand Casts', '2024-01-01', 'https://picsum.photos/400/400?random=51', 5);
INSERT INTO albums (titulo, fecha_lanzamiento, portada_url, artista_id) VALUES ('Dark Quickness', '2024-02-01', 'https://picsum.photos/400/400?random=52', 5);
INSERT INTO albums (titulo, fecha_lanzamiento, portada_url, artista_id) VALUES ('Arcane Spells', '2024-03-01', 'https://picsum.photos/400/400?random=53', 5);

-- Tracks (Poblando algunos archivos representativos)
-- Bisqo Dark - Magic Cast (Album 1)
INSERT INTO tracks (titulo, duracion, stream_url, portada_url, album_id, artista_id) VALUES ('Fire Liquid 001', 5, '/actividad/stream/855344__bisqo__dsgnsrce_dark-magic-cast-rise-fire-liquid-001_gmcm_fs1dm.wav', '', 1, 1);
INSERT INTO tracks (titulo, duracion, stream_url, portada_url, album_id, artista_id) VALUES ('Fire Liquid 002', 4, '/actividad/stream/855345__bisqo__dsgnsrce_dark-magic-cast-rise-fire-liquid-002_gmcm_fs1dm.wav', '', 1, 1);
INSERT INTO tracks (titulo, duracion, stream_url, portada_url, album_id, artista_id) VALUES ('Fire Liquid 003', 5, '/actividad/stream/855346__bisqo__dsgnsrce_dark-magic-cast-rise-fire-liquid-003_gmcm_fs1dm.wav', '', 1, 1);

-- Bisqo Dark - Deep Impacts (Album 2)
INSERT INTO tracks (titulo, duracion, stream_url, portada_url, album_id, artista_id) VALUES ('Deep Impact 001', 3, '/actividad/stream/855356__bisqo__dsgnsrce_deep-dark-magic-impact-short-kick-001_gmcm_fs1dm.wav', '', 2, 1);
INSERT INTO tracks (titulo, duracion, stream_url, portada_url, album_id, artista_id) VALUES ('Deep Impact 002', 3, '/actividad/stream/855357__bisqo__dsgnsrce_deep-dark-magic-impact-short-kick-002_gmcm_fs1dm.wav', '', 2, 1);

-- Wand Magic - Medium Casts (Album 4)
INSERT INTO tracks (titulo, duracion, stream_url, portada_url, album_id, artista_id) VALUES ('Medium Cast 001', 2, '/actividad/stream/855381__bisqo__dsgnsrce_magic-fire-wand-cast-medium-001_gmcm_fs1dm.wav', '', 4, 2);
INSERT INTO tracks (titulo, duracion, stream_url, portada_url, album_id, artista_id) VALUES ('Medium Cast 002', 2, '/actividad/stream/855382__bisqo__dsgnsrce_magic-fire-wand-cast-medium-002_gmcm_fs1dm.wav', '', 4, 2);

-- Shimmer Corrupt - Bell Spells (Album 7)
INSERT INTO tracks (titulo, duracion, stream_url, portada_url, album_id, artista_id) VALUES ('Bell Spell 001', 8, '/actividad/stream/855418__bisqo__dsgnsrce_shimmer-magic-corrupt-bell-spell-001_gmcm_fs1dm.wav', '', 7, 3);
INSERT INTO tracks (titulo, duracion, stream_url, portada_url, album_id, artista_id) VALUES ('Bell Spell 002', 7, '/actividad/stream/855419__bisqo__dsgnsrce_shimmer-magic-corrupt-bell-spell-002_gmcm_fs1dm.wav', '', 7, 3);

-- Magevil Ring - Debuff Rings (Album 10)
INSERT INTO tracks (titulo, duracion, stream_url, portada_url, album_id, artista_id) VALUES ('Debuff Ring 001', 9, '/actividad/stream/855427__bisqo__magevil_dark-magic-debuff-spell-ring-001_gmcm_fs1dm.wav', '', 10, 4);
INSERT INTO tracks (titulo, duracion, stream_url, portada_url, album_id, artista_id) VALUES ('Debuff Ring 002', 12, '/actividad/stream/855428__bisqo__magevil_dark-magic-debuff-spell-ring-002_gmcm_fs1dm.wav', '', 10, 4);

-- Magspel Quick - Wand Casts (Album 13)
INSERT INTO tracks (titulo, duracion, stream_url, portada_url, album_id, artista_id) VALUES ('Quick Spell 001', 4, '/actividad/stream/855436__bisqo__magspel_dark-magic-wand-spell-cast-001_gmcm_fs1dm.wav', '', 13, 5);
INSERT INTO tracks (titulo, duracion, stream_url, portada_url, album_id, artista_id) VALUES ('Quick Spell 002', 4, '/actividad/stream/855437__bisqo__magspel_dark-magic-wand-spell-cast-002_gmcm_fs1dm.wav', '', 13, 5);
