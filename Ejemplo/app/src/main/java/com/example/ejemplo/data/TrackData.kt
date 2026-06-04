package com.example.ejemplo.data

data class TrackData(
    val id: Long,
    val titulo: String,
    val artistaId: Long?,
    val artistaNombre: String,
    val albumTitulo: String,
    val portadaUrl: String,
    val streamUrl: String
)
