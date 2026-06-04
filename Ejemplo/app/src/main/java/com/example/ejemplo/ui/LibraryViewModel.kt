package com.example.ejemplo.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.ejemplo.data.local.AppDatabase
import com.example.ejemplo.data.local.LocalTrack
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class LibraryViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = AppDatabase.getDatabase(application).trackDao()
    private val currentUser = com.example.ejemplo.util.TokenManager.getUser(application) ?: "unknown"

    val favoriteTracks: StateFlow<List<LocalTrack>> = dao.getFavorites(currentUser)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun toggleFavorite(track: LocalTrack) {
        viewModelScope.launch {
            dao.updateFavoriteStatus(track.id, !track.isFavorite)
        }
    }

    fun isLiked(titulo: String, artista: String): kotlinx.coroutines.flow.Flow<Boolean> {
        return dao.isTrackFavorite(titulo, artista, currentUser)
    }

    fun removeTrackFromLibrary(trackData: com.example.ejemplo.data.TrackData) {
        viewModelScope.launch {
            dao.deleteTrackByDetails(trackData.titulo, trackData.artistaNombre, currentUser)
        }
    }

    fun addTrackToLibrary(trackData: com.example.ejemplo.data.TrackData) {
        viewModelScope.launch {
            val existingTrack = dao.findTrack(trackData.titulo, trackData.artistaNombre, currentUser)
            if (existingTrack == null) {
                val localTrack = LocalTrack(
                    userId = currentUser,
                    titulo = trackData.titulo,
                    artista = trackData.artistaNombre,
                    album = trackData.albumTitulo,
                    localPath = trackData.streamUrl,
                    isFavorite = true
                )
                dao.insertTrack(localTrack)
            }
        }
    }
}
