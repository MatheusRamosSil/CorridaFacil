package com.example.corridafacil.data.repository.auth.email

import android.net.Uri
import com.example.corridafacil.view.auth.viewModel.Result
import com.example.corridafacil.data.models.Passageiro
import com.google.firebase.auth.FirebaseUser

interface EmailRepository {

    //suspend fun createNewRegister(email: String, password: String): String
    //suspend fun singEmailPassword(email: String, password: String): Result
    //fun sendPasswordResetEmail(email: String):Boolean

    //fun userAuthenticated(): FirebaseUser?
    //fun logout()
    suspend fun updateImageProfile(uri: Uri, uidUser:String):String

    suspend fun saveNewUserInRealTimeDataBase(newInstancePassgeiro:Passageiro):Boolean
    //suspend fun sendEmailVerification():Void?

    //suspend fun generateNewTokenFCM(): String
    suspend fun checkUserDevice(uid: String): Passageiro
}