package com.example.ejemplo.util

import android.util.Log
import org.json.JSONObject
import retrofit2.Response

object ErrorParser {
    private const val TAG = "SpotifakeNetwork"

    fun parseError(response: Response<*>): String {
        val code = response.code()
        
        // Obtenemos el cuerpo del error sin consumirlo dos veces si es posible
        val errorBodyString = try {
            response.errorBody()?.string()
        } catch (e: Exception) {
            null
        }

        Log.e(TAG, "Full Error Body: $errorBodyString")

        return if (!errorBodyString.isNullOrBlank()) {
            try {
                val json = JSONObject(errorBodyString)
                val message = json.optString("message", "Error del servidor")
                Log.e(TAG, "API Error [$code]: $message")
                message
            } catch (e: Exception) {
                Log.e(TAG, "API Error [$code]: No JSON format. Body: $errorBodyString")
                "Error en el servidor ($code)"
            }
        } else {
            Log.e(TAG, "API Error [$code]: Empty error body")
            when(code) {
                401 -> "Usuario o contraseña incorrectos"
                403 -> "No tienes acceso a este contenido"
                404 -> "Recurso no encontrado"
                else -> "Error inesperado ($code)"
            }
        }
    }

    fun logException(e: Exception, context: String) {
        Log.e(TAG, "Exception in $context: ${e.message}", e)
    }
}
