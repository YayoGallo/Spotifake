package com.example.ejemplo.service

import com.example.ejemplo.data.AlbumData
import com.example.ejemplo.data.TrackData
import com.example.ejemplo.data.ArtistData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Path

interface ActividadService {
    @GET("actividad/recomendaciones")
    suspend fun getRecomendaciones(): Response<List<TrackData>>

    @GET("actividad/buscar")
    suspend fun buscar(@Query("query") query: String): Response<List<TrackData>>

    @GET("actividad/artista/{id}")
    suspend fun getArtista(@Path("id") id: Long): Response<ArtistData>

    @GET("actividad/album/{id}")
    suspend fun getAlbum(@Path("id") id: Long): Response<AlbumData>
}
