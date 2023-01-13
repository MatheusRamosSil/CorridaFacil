package com.example.corridafacil.data.repository.auth.email

import android.net.Uri
import android.util.Log
import com.example.corridafacil.domain.services.AuthenticationFirebaseSevice.Email.AuthenticationEmailFirebaseServiceImpl
import com.example.corridafacil.domain.services.FirebaseCloudStorage.FirebaseStorageCloud
import com.example.corridafacil.domain.services.FirebaseMenssaging.FirebaseMenssagingServices
import com.example.corridafacil.view.auth.viewModel.Result
import com.example.corridafacil.data.models.Passageiro
import com.example.corridafacil.data.models.dao.PassageiroDAO
import com.example.corridafacil.domain.services.AuthenticationFirebaseSevice.AuthenticathionEmail
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import javax.inject.Inject

class EmailRepositoryImpl @Inject constructor(
                          private val passageiroDAO: PassageiroDAO,
                          private val authenticathionEmail: AuthenticathionEmail,
                          private val firebaseStorageCloud: FirebaseStorageCloud
) : EmailRepository {


    /*override suspend fun createNewRegister(email: String, password: String): String {
        return authenticationEmailFirebaseServiceImpl.createNewAccountEmailPassword(email, password)
    }

     */

   /* override suspend fun singEmailPassword(email: String, password: String): Result {
        try {
            authenticationEmailFirebaseServiceImpl.singInEmailAndPassword(email, password)
            val result = authenticationEmailFirebaseServiceImpl.userAuthenticated()!!.isEmailVerified
            updateTokenInDataBase()
            return Result.Success(true)
        } catch (error: Exception) {
            return Result.Error(error)
        }
    }

    */

   /* fun updateTokenInDataBase(){
        CoroutineScope(IO).launch {
            try {
                val uidUser = authenticationEmailFirebaseServiceImpl.userAuthenticated()!!.uid
                val token = generateNewTokenFCM()
                val updateTokenFCm = mapOf("tokenPassageiro" to token)
                passageiroDAO.updateUser(uidUser,updateTokenFCm)
            }catch (exception:Exception){
                Log.w("Error in update token", exception.message.toString())
            }
        }

    }

    */

    override suspend fun generateNewTokenFCM(): String {
        return FirebaseMenssagingServices().generateTokenFCM()
    }

    override suspend fun checkUserDevice(uid: String): Passageiro {
        return passageiroDAO.readDataUser(uid)
    }

    /*override fun sendPasswordResetEmail(email: String): Boolean {
        return authenticationEmailFirebaseServiceImpl.sendPasswordResetEmail(email)
    }

     */

   /* override fun userAuthenticated(): FirebaseUser? {
        return authenticationEmailFirebaseServiceImpl.userAuthenticated()

    }

    */


  /*  override fun logout() {
        authenticationEmailFirebaseServiceImpl.logout()
    }

   */

    override suspend fun saveNewUserInRealTimeDataBase(newInstancePassageiro: Passageiro): Boolean
    {
        return passageiroDAO.createNewPassageiro(newInstancePassageiro)
    }

   /* override suspend fun sendEmailVerification(): Void? {
        return authenticationEmailFirebaseServiceImpl.sendEmailVerification()
    }

    */


    override suspend fun updateImageProfile(
        uri: Uri,
        uidUser: String,
    ): String {
        return firebaseStorageCloud.uploadImageProfile(uri,uidUser)
    }

}