package com.example.ejemplo.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ejemplo.client.ApiClient
import com.example.ejemplo.data.TrackData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class SearchState {
    object Idle : SearchState()
    object Loading : SearchState()
    data class Success(val tracks: List<TrackData>) : SearchState()
    data class Error(val message: String) : SearchState()
}

class SearchViewModel(private val service: com.example.ejemplo.service.ActividadService = ApiClient.actividadService) : ViewModel() {

    private val _state = MutableStateFlow<SearchState>(SearchState.Idle)
    val state: StateFlow<SearchState> = _state

    fun search(query: String) {
        if (query.isBlank()) {
            _state.value = SearchState.Idle
            return
        }

        viewModelScope.launch {
            _state.value = SearchState.Loading
            try {
                val response = service.buscar(query)
                if (response.isSuccessful) {
                    _state.value = SearchState.Success(response.body() ?: emptyList())
                } else {
                    val message = com.example.ejemplo.util.ErrorParser.parseError(response)
                    _state.value = SearchState.Error(message)
                }
            } catch (e: Exception) {
                com.example.ejemplo.util.ErrorParser.logException(e, "SearchViewModel")
                _state.value = SearchState.Error("Fallo en la búsqueda")
            }
        }
    }
}
