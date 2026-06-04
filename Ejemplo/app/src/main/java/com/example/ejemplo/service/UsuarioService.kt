package com.example.ejemplo.service

import com.example.ejemplo.data.LoginData
import com.example.ejemplo.data.LoginRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UsuarioService {

    @POST("usuarios/login")
    suspend fun  login(
        @Body request: LoginRequest
    ) : Response<LoginData>

    @POST("usuarios/getAll")
    suspend fun getAll(): Response<List<LoginData>>

    @POST("usuarios/add")
    suspend fun add(@Body request: LoginData): Response<LoginData>

    @POST("usuarios/edit")
    suspend fun edit(@Body request: LoginData): Response<LoginData>

    @POST("usuarios/delete")
    suspend fun delete(@Body request: LoginData): Response<Void>

}