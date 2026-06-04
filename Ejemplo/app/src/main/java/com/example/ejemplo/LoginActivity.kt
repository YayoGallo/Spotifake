package com.example.ejemplo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.lifecycle.lifecycleScope
import com.example.ejemplo.client.ApiClient
import com.example.ejemplo.data.LoginRequest
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var etUsuario: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var btnChangeLanguage: Button
    private lateinit var tvGoToRegister: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ApiClient.init(this)
        setContentView(R.layout.activity_login)

        etUsuario = findViewById(R.id.username)
        etPassword = findViewById(R.id.password)
        btnLogin = findViewById(R.id.aceptarLogin)
        btnChangeLanguage = findViewById(R.id.btnChangeLanguage)
        tvGoToRegister = findViewById(R.id.tvGoToRegister)

        btnChangeLanguage.setOnClickListener {
            val currentLocale = AppCompatDelegate.getApplicationLocales().toLanguageTags()
            val newLocale = if (currentLocale.contains("en")) "es" else "en"
            AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(newLocale))
        }

        btnLogin.setOnClickListener {
            val username = etUsuario.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, getString(R.string.error_fields), Toast.LENGTH_SHORT).show()
            } else {
                doLogin(username, password)
            }
        }

        tvGoToRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun doLogin(user: String, pass: String) {
        lifecycleScope.launch {
            try {
                val request = LoginRequest(user, pass)
                val response = ApiClient.usuarioService.login(request)

                if (response.isSuccessful) {
                    val usuarioResponse = response.body()
                    if (usuarioResponse != null && !usuarioResponse.token.isNullOrEmpty()) {
                        com.example.ejemplo.util.TokenManager.saveToken(this@LoginActivity, usuarioResponse.token!!)
                        com.example.ejemplo.util.TokenManager.saveUser(this@LoginActivity, usuarioResponse.usuario)
                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        intent.putExtra("username", usuarioResponse.usuario)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this@LoginActivity, getString(R.string.error_invalid_session), Toast.LENGTH_SHORT).show()
                    }
                } else {
                    val message = com.example.ejemplo.util.ErrorParser.parseError(response)
                    Toast.makeText(this@LoginActivity, message, Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                com.example.ejemplo.util.ErrorParser.logException(e, "LoginActivity")
                Toast.makeText(this@LoginActivity, getString(R.string.error_connection), Toast.LENGTH_SHORT).show()
            }
        }
    }
}