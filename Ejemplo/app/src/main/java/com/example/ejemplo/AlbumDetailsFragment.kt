package com.example.ejemplo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import com.example.ejemplo.client.ApiClient
import com.example.ejemplo.data.AlbumData
import com.example.ejemplo.ui.SpotifakeTheme

class AlbumDetailsFragment : Fragment() {

    private var albumId: Long = -1

    companion object {
        fun newInstance(id: Long): AlbumDetailsFragment {
            val fragment = AlbumDetailsFragment()
            val args = Bundle()
            args.putLong("albumId", id)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        albumId = arguments?.getLong("albumId") ?: -1
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return ComposeView(requireContext()).apply {
            setContent {
                SpotifakeTheme {
                    AlbumDetailsScreen(albumId)
                }
            }
        }
    }
}

@Composable
fun AlbumDetailsScreen(albumId: Long) {
    val context = LocalContext.current
    var album by remember { mutableStateOf<AlbumData?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(albumId) {
        try {
            val response = ApiClient.actividadService.getAlbum(albumId)
            if (response.isSuccessful) {
                album = response.body()
            } else {
                error = "Error al cargar álbum"
            }
        } catch (e: Exception) {
            error = "Error de conexión"
        } finally {
            isLoading = false
        }
    }

    if (isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
        }
    } else if (error != null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(error!!, color = MaterialTheme.colorScheme.error)
        }
    } else album?.let {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp)
        ) {
            item {
                IconButton(onClick = { (context as? android.app.Activity)?.onBackPressed() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = MaterialTheme.colorScheme.onBackground)
                }
                Text(
                    text = it.titulo,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = it.artistaNombre,
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(24.dp))
            }

            it.tracks?.let { tracks ->
                items(tracks) { track ->
                    // Asumiendo que SearchTrackItem está definido en otro lugar accesible
                    SearchTrackItem(track)
                }
            }
        }
    }
}
