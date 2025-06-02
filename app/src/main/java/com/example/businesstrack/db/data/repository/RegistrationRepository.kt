
package com.example.businesstrack.db.data.repository

import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject
import kotlinx.coroutines.tasks.await

class RegistrationRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) {
    suspend fun register(name: String, email: String, password: String) {
        firebaseAuth.createUserWithEmailAndPassword(email, password).await()

        val user = firebaseAuth.currentUser
        user?.updateProfile(com.google.firebase.auth.UserProfileChangeRequest.Builder()
            .setDisplayName(name)
            .build()
        )?.await()
    }
}
