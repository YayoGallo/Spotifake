package com.example.ejemplo.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.ejemplo.client.ApiClient
import com.example.ejemplo.data.LoginData
import kotlinx.coroutines.launch

@Composable
fun UserCrudScreen() {
    val coroutineScope = rememberCoroutineScope()
    var users by remember { mutableStateOf<List<LoginData>>(emptyList()) }
    var showDialog by remember { mutableStateOf(false) }
    var editingUser by remember { mutableStateOf<LoginData?>(null) }

    fun loadUsers() {
        coroutineScope.launch {
            try {
                val response = ApiClient.usuarioService.getAll()
                if (response.isSuccessful) {
                    users = response.body() ?: emptyList()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    LaunchedEffect(Unit) {
        loadUsers()
    }

    Column(modifier = Modifier.fillMaxSize().background(Color(0xFF191414)).padding(16.dp)) {
        Text(
            text = "Gestión de Usuarios",
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
            color = Color.White,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Button(
            onClick = {
                editingUser = null
                showDialog = true
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1DB954)),
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        ) {
            Text("Agregar Nuevo Usuario", color = Color.White)
        }

        LazyColumn(modifier = Modifier.weight(1f)) {
            items(users) { user ->
                UserCard(
                    user = user,
                    onEdit = {
                        editingUser = user
                        showDialog = true
                    },
                    onDelete = {
                        coroutineScope.launch {
                            try {
                                ApiClient.usuarioService.delete(user)
                                loadUsers()
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                    }
                )
            }
        }
    }

    if (showDialog) {
        UserDialog(
            user = editingUser,
            onDismiss = { showDialog = false },
            onSave = { updatedUser ->
                coroutineScope.launch {
                    try {
                        if (editingUser == null) {
                            ApiClient.usuarioService.add(updatedUser)
                        } else {
                            ApiClient.usuarioService.edit(updatedUser)
                        }
                        showDialog = false
                        loadUsers()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        )
    }
}

@Composable
fun UserCard(user: LoginData, onEdit: () -> Unit, onDelete: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF282828))
    ) {
        Row(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = user.usuario ?: "", fontWeight = FontWeight.Bold, color = Color.White)
                Text(text = "${user.nombre ?: ""} ${user.apellido ?: ""}", color = Color.Gray)
                Text(text = user.email ?: "", color = Color.Gray, style = MaterialTheme.typography.bodySmall)
            }
            Row {
                IconButton(onClick = onEdit) {
                    Icon(Icons.Default.Edit, contentDescription = "Editar", tint = Color.White)
                }
                IconButton(onClick = onDelete) {
                    Icon(Icons.Default.Delete, contentDescription = "Eliminar", tint = Color.Red)
                }
            }
        }
    }
}

@Composable
fun UserDialog(user: LoginData?, onDismiss: () -> Unit, onSave: (LoginData) -> Unit) {
    var username by remember { mutableStateOf(user?.usuario ?: "") }
    var password by remember { mutableStateOf("") }
    var nombre by remember { mutableStateOf(user?.nombre ?: "") }
    var apellido by remember { mutableStateOf(user?.apellido ?: "") }
    var email by remember { mutableStateOf(user?.email ?: "") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (user == null) "Agregar Usuario" else "Editar Usuario", color = Color.White) },
        text = {
            Column {
                OutlinedTextField(
                    value = username,
                    onValueChange = { username = it },
                    label = { Text("Usuario", color = Color.Gray) },
                    enabled = user == null,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    )
                )
                if (user == null) {
                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Password", color = Color.Gray) },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White
                        )
                    )
                }
                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text("Nombre", color = Color.Gray) },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    )
                )
                OutlinedTextField(
                    value = apellido,
                    onValueChange = { apellido = it },
                    label = { Text("Apellido", color = Color.Gray) },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    )
                )
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email", color = Color.Gray) },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    )
                )
            }
        },
        confirmButton = {
            Button(onClick = {
                onSave(LoginData(usuario = username, password = password, nombre = nombre, apellido = apellido, email = email))
            }, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1DB954))) {
                Text("Guardar", color = Color.White)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar", color = Color.Gray)
            }
        },
        containerColor = Color(0xFF282828)
    )
}
