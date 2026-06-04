package com.example.ejemplo.data

data class AlbumData(
    val id: Long,
    val titulo: String,
    val fechaLanzamiento: String, // String representation is enough for display
    val portadaUrl: String?,
    val artistaNombre: String,
    val tracks: List<TrackData>?
)
