package com.example.businesstrack.ui.screen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.businesstrack.db.data.repository.TransactionRepository
import com.example.businesstrack.db.domain.model.Transaction
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: TransactionRepository
) : ViewModel() {

    var transactions by mutableStateOf<List<Transaction>>(emptyList())
        private set

    var balance by mutableStateOf(0.0)
        private set

    init {
        loadTransactions()
    }

    fun loadTransactions() {
        repository.getTransactions { result ->
            transactions = result
            balance = result.sumOf {
                if (it.type == "income") it.amount else -it.amount
            }
        }
    }
}
