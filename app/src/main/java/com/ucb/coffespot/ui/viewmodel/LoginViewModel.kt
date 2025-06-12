package com.ucb.coffespot.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ucb.coffespot.ui.state.UiState
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val auth: FirebaseAuth
) : ViewModel() {

    private val _loginState = MutableStateFlow<UiState<Unit>>(UiState.Idle)
    val loginState: StateFlow<UiState<Unit>> = _loginState

    fun loginWithEmail(email: String, password: String) {
        if (email.isBlank() || password.length < 6) {
            _loginState.value = UiState.Error("Email o contraseña inválidos")
            return
        }
        _loginState.value = UiState.Loading
        viewModelScope.launch {
            auth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    _loginState.value = UiState.Success(Unit)
                }
                .addOnFailureListener { e ->
                    _loginState.value = UiState.Error(e.message ?: "Error desconocido")
                }
        }
    }

    // Opcional: método para Google Sign-In
    fun loginWithGoogle(idToken: String) {
        _loginState.value = UiState.Loading
        val credential = com.google.firebase.auth.GoogleAuthProvider.getCredential(idToken, null)
        viewModelScope.launch {
            auth.signInWithCredential(credential)
                .addOnSuccessListener {
                    _loginState.value = UiState.Success(Unit)
                }
                .addOnFailureListener { e ->
                    _loginState.value = UiState.Error(e.message ?: "Error Google Sign-In")
                }
        }
    }
}