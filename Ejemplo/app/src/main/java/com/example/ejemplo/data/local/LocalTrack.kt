package com.example.ejemplo.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "local_tracks")
data class LocalTrack(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val userId: String,
    val titulo: String,
    val artista: String,
    val album: String,
    val localPath: String?,
    val isDownloaded: Boolean = false,
    val isFavorite: Boolean = false,
    val fechaDescarga: Long = System.currentTimeMillis()
)
