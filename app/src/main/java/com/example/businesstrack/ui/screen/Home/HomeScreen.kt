package com.example.businesstrack.ui.screen.Home


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.businesstrack.ui.component.TransactionItem

@Composable
fun HomeScreen(
    onAddTransactionClick: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()

) {
    val transactions = viewModel.transactions
    val balance = viewModel.balance

    LaunchedEffect(Unit) {
        viewModel.loadTransactions()
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
        .systemBarsPadding()
    ) {
        Text("Общий баланс: $balance ₽", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        Text("Последние операции", style = MaterialTheme.typography.titleMedium)

        LazyColumn{
            items(transactions) { tx ->
                TransactionItem(
                    transaction = tx,
                    onEdit = {  },
                    onDelete = {
                        viewModel.deleteTransaction(tx.id) {
                            if (it) viewModel.loadTransactions()
                        }
                    }
                )
            }
        }


        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onAddTransactionClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Добавить транзакцию")
        }
    }
}
