package com.example.businesstrack.db.data.repository

import com.example.businesstrack.db.domain.model.Transaction
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import jakarta.inject.Inject

class TransactionRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    private val userId: String
        get() = Firebase.auth.currentUser?.uid ?: "anonymous"


    fun addTransaction(transaction: Transaction, onResult: (Boolean) -> Unit) {
        firestore.collection("users")
            .document(userId)
            .collection("transactions")
            .add(transaction)
            .addOnSuccessListener { onResult(true) }
            .addOnFailureListener { onResult(false) }
    }

    fun getTransactions(onResult: (List<Transaction>) -> Unit) {
        firestore.collection("users")
            .document(userId)
            .collection("transactions")
            .get()
            .addOnSuccessListener { result ->
                val list = result.toObjects(Transaction::class.java)
                onResult(list)
            }
    }

    fun deleteTransaction(id: String, onResult: (Boolean) -> Unit) {
        firestore.collection("users")
            .document(userId)
            .collection("transactions")
            .document(id)
            .delete()
            .addOnSuccessListener { onResult(true) }
            .addOnFailureListener { onResult(false) }
    }

    fun updateTransaction(id: String, updated: Transaction, onResult: (Boolean) -> Unit) {
        firestore.collection("users")
            .document(userId)
            .collection("transactions")
            .document(id)
            .set(updated)
            .addOnSuccessListener { onResult(true) }
            .addOnFailureListener { onResult(false) }
    }
}
