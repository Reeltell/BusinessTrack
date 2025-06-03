package com.example.businesstrack.ui.screen.AddTransaction

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.businesstrack.db.data.repository.TransactionRepository
import com.example.businesstrack.db.domain.model.Transaction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject



@HiltViewModel
class AddTransactionViewModel @Inject constructor(
    private val repository: TransactionRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddTransactionUiState())
    val uiState: StateFlow<AddTransactionUiState> = _uiState

    fun onCategoryChanged(category: String) {
        _uiState.value = _uiState.value.copy(
            category = category,
            isValid = validateForm(category, _uiState.value.amount)
        )
    }

    fun onAmountChanged(amount: String) {
        _uiState.value = _uiState.value.copy(
            amount = amount,
            isValid = validateForm(_uiState.value.category, amount)
        )
    }

    fun onNoteChanged(note: String) {
        _uiState.value = _uiState.value.copy(note = note)
    }

    fun setType(type: String) {
        _uiState.value = _uiState.value.copy(type = type)
    }

    fun saveTransaction(onSuccess: () -> Unit) {
        val state = _uiState.value
        val amount = state.amount.toDoubleOrNull()
        if (amount == null || state.category.isBlank()) return

        val transaction = Transaction(
            category = state.category,
            amount = amount,
            note = state.note,
            type = state.type,
            timestamp = System.currentTimeMillis()
        )

        viewModelScope.launch {
            repository.addTransaction(transaction) { success ->
                Log.d("AddTransactionViewModel", "Результат сохранения: $success")
                if (success) onSuccess()
            }
        }
    }

    private fun validateForm(category: String, amount: String): Boolean {
        return category.isNotBlank() && amount.toDoubleOrNull() != null
    }
}
