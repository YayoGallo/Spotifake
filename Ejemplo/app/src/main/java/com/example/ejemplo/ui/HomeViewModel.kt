package com.example.ejemplo.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ejemplo.client.ApiClient
import com.example.ejemplo.data.TrackData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class HomeState {
    object Loading : HomeState()
    data class Success(val tracks: List<TrackData>) : HomeState()
    data class Error(val message: String) : HomeState()
}

class HomeViewModel(private val service: com.example.ejemplo.service.ActividadService = ApiClient.actividadService) : ViewModel() {

    private val _state = MutableStateFlow<HomeState>(HomeState.Loading)
    val state: StateFlow<HomeState> = _state

    init {
        fetchRecomendaciones()
    }

    fun fetchRecomendaciones() {
        viewModelScope.launch {
            _state.value = HomeState.Loading
            try {
                val response = service.getRecomendaciones()
                if (response.isSuccessful) {
                    _state.value = HomeState.Success(response.body() ?: emptyList())
                } else {
                    val message = com.example.ejemplo.util.ErrorParser.parseError(response)
                    _state.value = HomeState.Error(message)
                }
            } catch (e: Exception) {
                com.example.ejemplo.util.ErrorParser.logException(e, "HomeViewModel")
                _state.value = HomeState.Error("Sin conexión con el servidor")
            }
        }
    }
}
