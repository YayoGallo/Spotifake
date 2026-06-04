package com.example.ejemplo

import com.example.ejemplo.service.ActividadService
import com.example.ejemplo.data.TrackData
import com.example.ejemplo.ui.HomeState
import com.example.ejemplo.ui.HomeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: HomeViewModel
    private val mockService = mock(ActividadService::class.java)

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `fetchRecomendaciones success updates state to Success`() = runTest {
        val mockTracks = listOf(TrackData(1, "Test Track", "Artist", "Album", "url", "stream"))
        `when`(mockService.getRecomendaciones()).thenReturn(Response.success(mockTracks))

        viewModel = HomeViewModel(mockService)
        
        // El init llama a fetchRecomendaciones, pero como es un StandardTestDispatcher, 
        // necesitamos avanzar el tiempo o llamar explícitamente si fuera necesario.
        // Pero el viewModelScope.launch se ejecuta en el dispatcher.
        
        advanceUntilIdle()

        val state = viewModel.state.value
        assertTrue(state is HomeState.Success)
        assertEquals(mockTracks, (state as HomeState.Success).tracks)
    }

    @Test
    fun `fetchRecomendaciones error updates state to Error`() = runTest {
        `when`(mockService.getRecomendaciones()).thenReturn(Response.error(404, mock(okhttp3.ResponseBody::class.java)))

        viewModel = HomeViewModel(mockService)
        advanceUntilIdle()

        val state = viewModel.state.value
        assertTrue(state is HomeState.Error)
        assertEquals("Error: 404", (state as HomeState.Error).message)
    }
}
