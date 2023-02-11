package com.example.corridafacil.view.auth.viewModel

import android.os.Build
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.corridafacil.data.models.Passageiro
import com.example.corridafacil.data.repository.auth.AuthEmailRepository
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject


sealed class Result{
    data class Success(val status: Boolean) : Result()
    data class Error(val exception: Exception) : Result()
    data class LocationDevice(val locationDevice: LatLng):Result()
    object Empty: Result()

}

@HiltViewModel
class EmailViewModel @Inject constructor(
    private val authEmailRepository: AuthEmailRepository,
): ViewModel() {

    private lateinit var tokenFirebase : String
    private var typeDevie : String
    private var modelDevice : String
    private lateinit var auth: FirebaseAuth

    val _state = MutableStateFlow<Result>(Result.Empty)
    val stateUI : StateFlow<Result> = _state

    var addValuesToOneNewUser = HashMap<String,String>()

    init {
        viewModelScope.async {
            //tokenFirebase = emailRepository.generateNewTokenFCM()
        }

        typeDevie = Build.DEVICE
        modelDevice = Build.MODEL+" ("+Build.PRODUCT+")"
    }


    /*fun checkDeviceAndEmailOfLoggedUser(){
        viewModelScope.async {
            try {
                val result = checkUserAuthenticated()!!.isEmailVerified
                val verifyModelDevice = checkUserDevice()
                Log.w("Check device & email", " email: "+result)
                if (result && verifyModelDevice){
                    _state.value = Result.Success(true)
                }

            }catch (exception : Exception){ }
        }

    }

     */

    /*fun checkUserAuthenticated(): FirebaseUser? {
        return emailRepository.userAuthenticated()
    }

    suspend fun checkUserDevice() : Boolean{
        try {
            val uid = checkUserAuthenticated()?.uid
            val dataUser = uid?.let { emailRepository.checkUserDevice(it) }
            Log.w("Device model ", dataUser?.modeloDoDispositivo.toString())
            if(!dataUser?.modeloDoDispositivo.equals(modelDevice)){
                return false
            }
        }catch (e : Exception){}

        return true
    }

     */

 /*   fun logout(){
        emailRepository.logout()
    }

  */



    fun login(email: String, password: String) {
        auth = Firebase.auth

        viewModelScope.async {
            authEmailRepository
                 .singInEmailPassword(email,password,auth)
        }
        if (auth.currentUser != null){
            _state.value = Result.Success(true)
        }
    }
     /*
    fun forgotPassword(emailUser: String){
        val result = emailRepository.sendPasswordResetEmail(emailUser)
        _state.value = Result.Success(result)
    }

      */

   /* fun register(uriImage:Uri ,email:String, password: String){
        viewModelScope.async {
            try {
                val uid = emailRepository.createNewRegister(email, password)
                _state.value = Result.Success(true)
                val url = emailRepository.updateImageProfile(uriImage,uid)
                val newUser = createNewUser(uid,url,true)
                emailRepository.saveNewUserInRealTimeDataBase(newUser)
                emailRepository.sendEmailVerification()
            }catch (error : Exception){
                _state.value = Result.Error(error)
            }
        }
    }

    */

    fun createNewUser(uidUser: String,urlDaImagem: String,statusActivated : Boolean): Passageiro {

        addValuesToOneNewUser.put("uidUser",uidUser)
        addValuesToOneNewUser.put("urlDaFoto",urlDaImagem)
        addValuesToOneNewUser.put("token",tokenFirebase)
        addValuesToOneNewUser.put("tipoDeDispositivo", typeDevie)
        addValuesToOneNewUser.put("modeloDoDispositivo",modelDevice)

        return ResgitrationViewParams(addValuesToOneNewUser,statusActivated).create()

    }



}










