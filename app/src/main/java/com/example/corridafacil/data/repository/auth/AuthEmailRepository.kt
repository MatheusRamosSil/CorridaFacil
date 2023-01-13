package com.example.corridafacil.data.repository.auth

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

interface AuthEmailRepository {
    suspend fun createNewRegister(email: String, password: String,): String
    suspend fun singInEmailPassword(email: String, password: String,firebaseAuth: FirebaseAuth): AuthResult?
    suspend fun sendEmailVerification()
    fun logout()

}