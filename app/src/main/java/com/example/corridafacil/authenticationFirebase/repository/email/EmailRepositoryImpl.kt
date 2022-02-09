package com.example.corridafacil.authenticationFirebase.repository.email

import android.net.Uri
import com.example.corridafacil.Services.AuthenticationFirebaseSevice.Email.AuthenticationEmailFirebaseServiceImpl
import com.example.corridafacil.Services.FirebaseCloudStorage.FirebaseStorageCloud
import com.example.corridafacil.Services.FirebaseMenssaging.FirebaseMenssagingServices
import com.example.corridafacil.authenticationFirebase.viewModel.Result
import com.example.corridafacil.models.Passageiro
import com.example.corridafacil.models.dao.PassageiroDAO
import com.google.firebase.auth.FirebaseUser

class EmailRepositoryImpl(private val passageiroDAO: PassageiroDAO,
                          private val authenticationEmailFirebaseServiceImpl: AuthenticationEmailFirebaseServiceImpl,
                          private val firebaseStorageCloud: FirebaseStorageCloud
)
    : EmailRepository {


    override suspend fun createNewRegister(email: String, password: String): String {
        return authenticationEmailFirebaseServiceImpl.createNewAccountEmailPassword(email, password)
    }

    override suspend fun singEmailPassword(email: String, password: String): Result {
        try {
            authenticationEmailFirebaseServiceImpl.singInEmailAndPassword(email, password)
            val result = authenticationEmailFirebaseServiceImpl.userAuthenticated()!!.isEmailVerified
            val uidUser = authenticationEmailFirebaseServiceImpl.userAuthenticated()!!.uid
            val token = generateNewTokenFCM()
            val updateTokenFCm = mapOf("tokenPassageiro" to token)
            passageiroDAO.updateUser(uidUser,updateTokenFCm)
            return Result.Success(result)
        } catch (error: Exception) {
            return Result.Error(error)
        }
    }

    override suspend fun generateNewTokenFCM(): String {
        return FirebaseMenssagingServices().generateTokenFCM()
    }
    override fun sendPasswordResetEmail(email: String): Boolean {
        return authenticationEmailFirebaseServiceImpl.sendPasswordResetEmail(email)
    }

    override fun userAuthenticated(): FirebaseUser? {
        return authenticationEmailFirebaseServiceImpl.userAuthenticated()

    }

    override fun logout() {
        authenticationEmailFirebaseServiceImpl.logout()
    }

    override suspend fun saveNewUserInRealTimeDataBase(newInstancePassageiro: Passageiro): Boolean
    {
        return passageiroDAO.createNewPassageiro(newInstancePassageiro)
    }

    override suspend fun sendEmailVerification(): Void? {
        return authenticationEmailFirebaseServiceImpl.sendEmailVerification()
    }


    override suspend fun updateImageProfile(
        uri: Uri,
        uidUser: String,
    ): String {
        return firebaseStorageCloud.uploadImageProfile(uri,uidUser)
    }

}