package com.example.ejemplo.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ejemplo.R
import com.example.ejemplo.service.SpotifakePlayer

@Composable
fun FullPlayerScreen(onDismiss: () -> Unit) {
    val libraryViewModel: LibraryViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
    val isPlaying by SpotifakePlayer.isPlaying.collectAsState()
    val track by SpotifakePlayer.currentTrack.collectAsState()
    val position by SpotifakePlayer.currentPosition.collectAsState()
    val duration by SpotifakePlayer.duration.collectAsState()
    var sliderPosition by remember { mutableStateOf<Float?>(null) }
    val isLiked by libraryViewModel.isLiked(track?.titulo ?: "", track?.artistaNombre ?: "").collectAsState(initial = false)

    BackHandler { onDismiss() }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Carátula real
            Box(
                modifier = Modifier
                    .size(320.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant),
                contentAlignment = Alignment.Center
            ) {
                if (track?.portadaUrl.isNullOrEmpty()) {
                    Icon(
                        Icons.Default.MusicNote,
                        contentDescription = null,
                        modifier = Modifier.size(100.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                } else {
                    coil.compose.AsyncImage(
                        model = track?.portadaUrl,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = androidx.compose.ui.layout.ContentScale.Crop
                    )
                }
            }

            Spacer(modifier = Modifier.height(48.dp))

            Text(
                text = track?.titulo ?: androidx.compose.ui.res.stringResource(id = com.example.ejemplo.R.string.no_track),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = track?.artistaNombre ?: "Spotifake Original",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(32.dp))

            Slider(
                value = sliderPosition ?: position.toFloat(),
                onValueChange = { sliderPosition = it },
                onValueChangeFinished = {
                    sliderPosition?.let { SpotifakePlayer.seekTo(it.toLong()) }
                    sliderPosition = null
                },
                valueRange = 0f..duration.toFloat().coerceAtLeast(1f),
                colors = SliderDefaults.colors(
                    thumbColor = MaterialTheme.colorScheme.primary,
                    activeTrackColor = MaterialTheme.colorScheme.primary,
                    inactiveTrackColor = Color.Gray
                )
            )

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(formatTime(position), fontSize = 12.sp, color = Color.Gray)
                Text(formatTime(duration), fontSize = 12.sp, color = Color.Gray)
            }

            Spacer(modifier = Modifier.height(32.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    IconButton(onClick = { 
                        track?.let { 
                            if (isLiked) libraryViewModel.removeTrackFromLibrary(it)
                            else libraryViewModel.addTrackToLibrary(it)
                        }
                    }) {
                        Icon(
                            Icons.Default.Favorite, 
                            contentDescription = null, 
                            tint = if (isLiked) Color.Green else Color.Gray
                        )
                    }
                    Text(
                        text = androidx.compose.ui.res.stringResource(id = com.example.ejemplo.R.string.like),
                        color = Color.White,
                        fontSize = 10.sp
                    )
                }
                IconButton(onClick = { SpotifakePlayer.rewind() }) {
                    Icon(Icons.Default.Replay10, contentDescription = null, modifier = Modifier.size(40.dp), tint = Color.White)
                }
                FloatingActionButton(
                    onClick = { SpotifakePlayer.pauseResume() },
                    containerColor = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(50)
                ) {
                    Icon(
                        imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                        contentDescription = null,
                        modifier = Modifier.size(40.dp)
                    )
                }
                IconButton(onClick = { SpotifakePlayer.forward() }) {
                    Icon(Icons.Default.Forward10, contentDescription = null, modifier = Modifier.size(40.dp), tint = Color.White)
                }
                IconButton(onClick = onDismiss) {
                    Icon(Icons.Default.KeyboardArrowDown, contentDescription = null, tint = Color.White)
                }
            }
        }
    }
}

fun formatTime(ms: Long): String {
    val totalSeconds = ms / 1000
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60
    return "%02d:%02d".format(minutes, seconds)
}
