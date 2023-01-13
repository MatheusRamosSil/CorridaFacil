package com.example.corridafacil.domain.services.AuthenticationFirebaseSevice

import android.util.Log
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import java.lang.Exception
import javax.inject.Inject

class AuthenticationFirebaseService @Inject constructor(): AuthenticathionEmail {

    override suspend fun singInEmailAndPassword(
        emailUsuario: String,
        password: String,
        firebaseAuth: FirebaseAuth
    ): AuthResult? {
        return try {
            val result = firebaseAuth.signInWithEmailAndPassword(emailUsuario,password).await()
            result
        }catch (e : Exception){
            Log.d("AuthResult", "${e.message}")
            null
        }


    }

    override suspend fun createUserWithEmailAndPassword(
        emailUsuario: String,
        password: String,
        firebaseAuth: FirebaseAuth
    ): AuthResult? {
        return try {
            val result = firebaseAuth.createUserWithEmailAndPassword(emailUsuario,password).await()
            result
        }catch (e : Exception){
            Log.d("AuthResult", "${e.message}")
            null
        }
    }

}