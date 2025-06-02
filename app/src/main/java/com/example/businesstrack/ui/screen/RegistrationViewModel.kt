package com.example.businesstrack.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.businesstrack.db.data.repository.RegistrationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val registrationRepository: RegistrationRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegistrationUiState())
    val uiState: StateFlow<RegistrationUiState> = _uiState.asStateFlow()

    fun onNameChanged(name: String) {
        _uiState.update { it.copy(name = name, error = null) }
        validateForm()
    }

    fun onEmailChanged(email: String) {
        _uiState.update { it.copy(email = email, error = null) }
        validateForm()
    }

    fun onPasswordChanged(password: String) {
        _uiState.update { it.copy(password = password, error = null) }
        validateForm()
    }

    private fun validateForm() {
        val state = _uiState.value
        val isValid = state.name.isNotBlank() &&
                state.email.contains("@") &&
                state.password.length >= 6

        _uiState.update { it.copy(isFormValid = isValid) }
    }

    fun registerUser(onSuccess: () -> Unit) {
        val state = _uiState.value
        viewModelScope.launch {
            try {
                registrationRepository.register(
                    name = state.name,
                    email = state.email,
                    password = state.password
                )
                onSuccess()
            } catch (e: Exception) {
                _uiState.update { it.copy(error = "Ошибка регистрации: ${e.message}") }
            }
        }
    }
}
