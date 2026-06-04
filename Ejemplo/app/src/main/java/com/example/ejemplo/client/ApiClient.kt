package com.example.ejemplo.client

import android.content.Context
import android.util.Log
import com.example.ejemplo.service.ActividadService
import com.example.ejemplo.service.UsuarioService
import com.example.ejemplo.util.TokenManager
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private const val TAG = "SpotifakeApiClient"
    private const val BASE_URL = "http://10.0.2.2:8080/"
    private var appContext: Context? = null

    fun init(context: Context) {
        appContext = context.applicationContext
    }

    private val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                val requestBuilder = chain.request().newBuilder()
                val token = appContext?.let { TokenManager.getToken(it) }
                
                if (token != null) {
                    Log.d(TAG, "Inyectando Token: Bearer ${token.take(10)}...")
                    requestBuilder.addHeader("Authorization", "Bearer $token")
                } else {
                    Log.w(TAG, "No se encontró token para la petición a ${chain.request().url}")
                }
                
                chain.proceed(requestBuilder.build())
            }
            .build()
    }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val usuarioService: UsuarioService by lazy {
        retrofit.create(UsuarioService::class.java)
    }

    val actividadService: ActividadService by lazy {
        retrofit.create(ActividadService::class.java)
    }
}
