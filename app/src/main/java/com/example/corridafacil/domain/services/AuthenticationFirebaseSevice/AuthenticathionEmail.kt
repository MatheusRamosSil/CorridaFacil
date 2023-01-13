package com.example.corridafacil.domain.services.AuthenticationFirebaseSevice

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

interface AuthenticathionEmail{

    suspend fun singInEmailAndPassword(emailUsuario: String, password: String, firebaseAuth: FirebaseAuth): AuthResult?
    suspend fun createUserWithEmailAndPassword(emailUsuario: String, password: String, firebaseAuth: FirebaseAuth): AuthResult?
}
