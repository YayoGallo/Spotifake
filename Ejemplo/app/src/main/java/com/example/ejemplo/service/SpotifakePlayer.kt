package com.example.ejemplo.service

import android.content.ComponentName
import android.content.Context
import android.util.Log
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.example.ejemplo.data.TrackData
import com.google.common.util.concurrent.ListenableFuture
import com.google.common.util.concurrent.MoreExecutors
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object SpotifakePlayer {
    private const val TAG = "SpotifakePlayer"
    private var controllerFuture: ListenableFuture<MediaController>? = null
    private val controller: MediaController?
        get() = if (controllerFuture?.isDone == true) controllerFuture?.get() else null
    
    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying

    private val _currentTrack = MutableStateFlow<TrackData?>(null)
    val currentTrack: StateFlow<TrackData?> = _currentTrack

    private val _currentTrackTitle = MutableStateFlow<String?>(null)
    val currentTrackTitle: StateFlow<String?> = _currentTrackTitle

    private val _currentPosition = MutableStateFlow(0L)
    val currentPosition: StateFlow<Long> = _currentPosition

    private val _duration = MutableStateFlow(0L)
    val duration: StateFlow<Long> = _duration

    private var job: Job? = null

    fun initialize(context: Context) {
        if (controllerFuture == null) {
            val sessionToken = SessionToken(context, ComponentName(context, PlaybackService::class.java))
            controllerFuture = MediaController.Builder(context, sessionToken).buildAsync()
            controllerFuture?.addListener({
                val controller = controller
                controller?.addListener(object : Player.Listener {
                    override fun onIsPlayingChanged(isPlaying: Boolean) {
                        _isPlaying.value = isPlaying
                        if (isPlaying) startPositionTracker() else stopPositionTracker()
                    }

                    override fun onPlaybackStateChanged(state: Int) {
                        if (state == Player.STATE_READY) {
                            _duration.value = controller.duration
                        } else if (state == Player.STATE_ENDED) {
                            _currentPosition.value = 0L
                            _isPlaying.value = false
                            stopPositionTracker()
                        }
                    }

                    override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
                        _currentTrackTitle.value = mediaItem?.mediaMetadata?.title?.toString()
                    }
                })
            }, MoreExecutors.directExecutor())
        }
    }

    private fun startPositionTracker() {
        job?.cancel()
        job = CoroutineScope(Dispatchers.Main).launch {
            while (isActive) {
                _currentPosition.value = controller?.currentPosition ?: 0L
                delay(1000)
            }
        }
    }

    private fun stopPositionTracker() {
        job?.cancel()
    }

    fun playTrack(track: TrackData) {
        _currentTrack.value = track
        _currentTrackTitle.value = track.titulo
        controller?.let {
            val mediaItem = MediaItem.Builder()
                .setUri(track.streamUrl)
                .setMediaId(track.id.toString())
                .setMediaMetadata(
                    androidx.media3.common.MediaMetadata.Builder()
                        .setTitle(track.titulo)
                        .setArtist(track.artistaNombre)
                        .setArtworkUri(android.net.Uri.parse(track.portadaUrl))
                        .build()
                )
                .build()
            it.setMediaItem(mediaItem)
            it.prepare()
            it.play()
        }
    }

    fun playTrack(localTrack: com.example.ejemplo.data.local.LocalTrack) {
        val trackData = TrackData(
            id = localTrack.id,
            titulo = localTrack.titulo,
            artistaId = null,
            artistaNombre = localTrack.artista,
            albumTitulo = localTrack.album,
            portadaUrl = "",
            streamUrl = localTrack.localPath ?: ""
        )
        playTrack(trackData)
    }

    fun pauseResume() {
        controller?.let {
            if (it.isPlaying) it.pause() else it.play()
        }
    }

    fun seekTo(position: Long) {
        controller?.seekTo(position)
    }

    fun forward() {
        controller?.let { it.seekTo(it.currentPosition + 10000) }
    }

    fun rewind() {
        controller?.let { it.seekTo(it.currentPosition - 10000) }
    }

    fun release() {
        stopPositionTracker()
        controller?.let {
            it.stop()
            it.release()
        }
        controllerFuture = null
        clearPlayerState()
    }

    private fun clearPlayerState() {
        _isPlaying.value = false
        _currentTrack.value = null
        _currentTrackTitle.value = null
        _currentPosition.value = 0L
        _duration.value = 0L
    }
}
