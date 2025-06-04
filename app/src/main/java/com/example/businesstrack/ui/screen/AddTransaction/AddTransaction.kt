package com.example.businesstrack.ui.screen.AddTransaction

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel


@Composable
fun AddTransactionScreen(
    viewModel: AddTransactionViewModel = hiltViewModel(),
    onTransactionAdded: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .systemBarsPadding(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Новая транзакция", style = MaterialTheme.typography.headlineSmall)

        OutlinedTextField(
            value = uiState.category,
            onValueChange = viewModel::onCategoryChanged,
            label = { Text("Категория") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = uiState.amount,
            onValueChange = viewModel::onAmountChanged,
            label = { Text("Сумма") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        OutlinedTextField(
            value = uiState.note,
            onValueChange = viewModel::onNoteChanged,
            label = { Text("Заметка") },
            modifier = Modifier.fillMaxWidth()
        )

        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            val selectedType = uiState.type

            Button(
                onClick = { viewModel.setType("income") },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (selectedType == "income") Color(0xFF4CAF50) else Color.LightGray,
                    contentColor = Color.White
                )
            ) {
                Text("Доход")
            }

            Button(
                onClick = { viewModel.setType("expense") },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (selectedType == "expense") Color(0xFFF44336) else Color.LightGray,
                    contentColor = Color.White
                )
            ) {
                Text("Расход")
            }
        }

        Button(
            onClick = {
                viewModel.saveTransaction {
                    onTransactionAdded()
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = uiState.isValid
        ) {
            Text("Сохранить")
        }
    }
}
