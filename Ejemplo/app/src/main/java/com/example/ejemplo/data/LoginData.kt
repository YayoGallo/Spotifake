package com.example.ejemplo.data

data class LoginData(

    val usuario: String,
    val password: String,
    val nombre: String,
    val apellido: String,
    val email: String,
    val token: String? = null

)
