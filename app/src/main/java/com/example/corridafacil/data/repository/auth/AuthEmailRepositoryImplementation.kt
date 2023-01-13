package com.example.corridafacil.data.repository.auth

import com.example.corridafacil.domain.services.AuthenticationFirebaseSevice.AuthenticathionEmail
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class AuthEmailRepositoryImplementation @Inject constructor(
    private val authenticathionEmail: AuthenticathionEmail
):AuthEmailRepository {

    override suspend fun createNewRegister(email: String, password: String): String {
        TODO("Not yet implemented")
    }

    override suspend fun singInEmailPassword(
        email: String,
        password: String,
        firebaseAuth: FirebaseAuth
    ): AuthResult? {
      return  authenticathionEmail.singInEmailAndPassword(email,password,firebaseAuth)
    }

    override suspend fun sendEmailVerification() {
        TODO("Not yet implemented")
    }

    override fun logout() {
        TODO("Not yet implemented")
    }
}