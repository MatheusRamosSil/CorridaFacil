package com.example.corridafacil.Services.AuthenticationFirebaseSevice.Email

import com.google.firebase.auth.FirebaseUser

interface AuthenticationEmailFirebaseService {

    suspend fun createNewAccountEmailPassword(emailUsuario:String, password:String):String

    suspend fun sendEmailVerification():Void?

    fun singInEmailAndPassword(emailUsuario: String, password: String, callbackAuthEmail: CallbackAuthEmail)

    fun sendPasswordResetEmail(email: String, callback: CallbackAuthEmail)

    fun userAuthenticated(): FirebaseUser?

    fun logout()

}