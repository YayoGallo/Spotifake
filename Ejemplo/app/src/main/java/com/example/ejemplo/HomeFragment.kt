package com.example.ejemplo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.viewModels
import coil.compose.AsyncImage
import com.example.ejemplo.data.TrackData
import com.example.ejemplo.ui.HomeState
import com.example.ejemplo.ui.HomeViewModel
import com.example.ejemplo.ui.SpotifakeTheme
import com.example.ejemplo.service.SpotifakePlayer

class HomeFragment : Fragment() {
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return ComposeView(requireContext()).apply {
            setContent {
                SpotifakeTheme {
                    HomeScreen(viewModel)
                }
            }
        }
    }
}

@Composable
fun HomeScreen(viewModel: HomeViewModel) {
    val state by viewModel.state.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
            .verticalScroll(androidx.compose.foundation.rememberScrollState())
    ) {
        Text(
            text = androidx.compose.ui.res.stringResource(id = R.string.welcome_back),
            fontSize = 24.sp,
            fontWeight = FontWeight.ExtraBold,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        when (val currentState = state) {
            is HomeState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                }
            }
            is HomeState.Success -> {
                val groupedTracks = currentState.tracks.groupBy { it.artistaNombre }
                
                groupedTracks.forEach { (artista, tracks) ->
                    Text(
                        text = androidx.compose.ui.res.stringResource(id = R.string.from_artist, artista),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(bottom = 12.dp, top = 16.dp)
                    )
                    TrackCarousel(tracks = tracks)
                }
            }
            is HomeState.Error -> {
                Text(text = currentState.message, color = MaterialTheme.colorScheme.error)
            }
        }
    }
}

@Composable
fun TrackCarousel(tracks: List<TrackData>) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(end = 16.dp)
    ) {
        items(tracks) { track ->
            TrackCard(track)
        }
    }
}

@Composable
fun TrackCard(track: TrackData) {
    val context = androidx.compose.ui.platform.LocalContext.current
    Column(
        modifier = Modifier
            .width(150.dp)
            .clickable { SpotifakePlayer.playTrack(track) }
    ) {
        AsyncImage(
            model = track.portadaUrl,
            contentDescription = null,
            modifier = Modifier
                .size(150.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = track.titulo,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurface,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = track.artistaNombre,
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.primary, // Cambiamos a verde
            maxLines = 1,
            modifier = Modifier.clickable {
                track.artistaId?.let { id ->
                    (context as? MainActivity)?.openArtistProfile(id)
                }
            }
        )
    }
}
