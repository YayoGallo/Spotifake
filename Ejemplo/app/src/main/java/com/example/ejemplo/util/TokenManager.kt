package com.example.ejemplo.util

import android.content.Context
import android.content.SharedPreferences

object TokenManager {
    private const val PREFS_NAME = "spotifake_prefs"
    private const val KEY_TOKEN = "jwt_token"
    private const val KEY_USER = "current_user"

    private fun getPrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun saveToken(context: Context, token: String) {
        getPrefs(context).edit().putString(KEY_TOKEN, token).apply()
    }

    fun getToken(context: Context): String? {
        return getPrefs(context).getString(KEY_TOKEN, null)
    }

    fun saveUser(context: Context, username: String) {
        getPrefs(context).edit().putString(KEY_USER, username).apply()
    }

    fun getUser(context: Context): String? {
        return getPrefs(context).getString(KEY_USER, null)
    }

    fun clearToken(context: Context) {
        getPrefs(context).edit().remove(KEY_TOKEN).remove(KEY_USER).apply()
    }
}
