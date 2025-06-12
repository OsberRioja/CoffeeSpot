package com.ucb.coffespot.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ucb.coffespot.ui.viewmodel.LoginViewModel
import com.ucb.coffespot.ui.state.UiState


@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onSignUpClick: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel() // si usas Hilt; sino omite este parámetro
) {
    // Estado local para los campos
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val uiState by viewModel.loginState.collectAsState()
    val context = LocalContext.current
    val state = uiState

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Inicio de Sesión", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Correo") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(16.dp))

        Button(
            onClick = { viewModel.loginWithEmail(email, password) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Ingresar")
        }
        Spacer(Modifier.height(8.dp))
        TextButton(onClick = onSignUpClick) {
            Text("¿No tienes cuenta? Regístrate")
        }
    }

    // Observa el estado de login
    LaunchedEffect(state) {
        when (val s = state) {
            is UiState.Error -> {
                // aquí s.message ya está disponible
                Toast.makeText(context, s.message, Toast.LENGTH_SHORT).show()
            }
            is UiState.Success -> {
                onLoginSuccess()
            }
            else -> {
                // Idle o Loading: podrías mostrar un ProgressIndicator si quieres
            }
        }
    }
}