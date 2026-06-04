package com.example.ejemplo

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
import com.example.ejemplo.data.LoginData
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {

    private lateinit var etName: EditText
    private lateinit var etEmail: EditText
    private lateinit var etUsername: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnRegister: Button
    private lateinit var btnChangeLanguage: Button
    private lateinit var tvBackToLogin: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        etName = findViewById(R.id.etRegName)
        etEmail = findViewById(R.id.etRegEmail)
        etUsername = findViewById(R.id.etRegUsername)
        etPassword = findViewById(R.id.etRegPassword)
        btnRegister = findViewById(R.id.btnRegister)
        btnChangeLanguage = findViewById(R.id.btnRegChangeLanguage)
        tvBackToLogin = findViewById(R.id.tvBackToLogin)

        btnChangeLanguage.setOnClickListener {
            val currentLocale = AppCompatDelegate.getApplicationLocales().toLanguageTags()
            val newLocale = if (currentLocale.contains("en")) "es" else "en"
            AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(newLocale))
        }

        btnRegister.setOnClickListener {
            val name = etName.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val user = etUsername.text.toString().trim()
            val pass = etPassword.text.toString().trim()

            if (name.isEmpty() || email.isEmpty() || user.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, getString(R.string.error_fields), Toast.LENGTH_SHORT).show()
            } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "Correo electrónico inválido", Toast.LENGTH_SHORT).show()
            } else {
                doRegister(name, email, user, pass)
            }
        }

        tvBackToLogin.setOnClickListener {
            finish()
        }
    }

    private fun doRegister(name: String, email: String, user: String, pass: String) {
        lifecycleScope.launch {
            try {
                // Usamos LoginData que es el equivalente a UsuarioDTO en el backend
                val dto = LoginData(
                    nombre = name,
                    apellido = "",
                    email = email,
                    usuario = user,
                    password = pass,
                    token = null
                )
                
                val response = ApiClient.usuarioService.add(dto)

                if (response.isSuccessful) {
                    Toast.makeText(this@RegisterActivity, getString(R.string.register_success), Toast.LENGTH_LONG).show()
                    finish()
                } else {
                    val message = com.example.ejemplo.util.ErrorParser.parseError(response)
                    Toast.makeText(this@RegisterActivity, message, Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                com.example.ejemplo.util.ErrorParser.logException(e, "RegisterActivity")
                Toast.makeText(this@RegisterActivity, getString(R.string.error_connection), Toast.LENGTH_SHORT).show()
            }
        }
    }
}
