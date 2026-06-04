package com.example.ejemplo.data

data class ArtistData(
    val id: Long,
    val nombre: String,
    val biografia: String,
    val fotoUrl: String?,
    val albums: List<AlbumData>,
    val tracks: List<TrackData>
)
