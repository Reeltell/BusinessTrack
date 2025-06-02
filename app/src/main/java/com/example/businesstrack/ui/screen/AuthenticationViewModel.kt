package com.example.businesstrack.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AuthenticationUiState(
    val email: String = "",
    val password: String = "",
    val isFormValid: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val auth: FirebaseAuth
) : ViewModel() {

    private val _uiState = MutableStateFlow(AuthenticationUiState())
    val uiState: StateFlow<AuthenticationUiState> = _uiState

    fun onEmailChanged(email: String) {
        _uiState.value = _uiState.value.copy(
            email = email,
            isFormValid = validateForm(email, _uiState.value.password)
        )
    }

    fun onPasswordChanged(password: String) {
        _uiState.value = _uiState.value.copy(
            password = password,
            isFormValid = validateForm(_uiState.value.email, password)
        )
    }

    private fun validateForm(email: String, password: String): Boolean {
        return email.isNotBlank() && password.length >= 6
    }

    fun loginUser(onSuccess: () -> Unit) {
        val email = _uiState.value.email
        val password = _uiState.value.password

        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                _uiState.value = _uiState.value.copy(error = null)
                onSuccess()
            }
            .addOnFailureListener { exception ->
                _uiState.value = _uiState.value.copy(error = exception.message)
            }
    }
}
