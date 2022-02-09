package com.example.corridafacil.authenticationFirebase.viewModel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.corridafacil.authenticationFirebase.repository.email.EmailRepository
import com.example.corridafacil.models.Passageiro
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.lang.Exception

class EmailViewModel(private val emailRepository: EmailRepository): ViewModel() {

    private lateinit var tokenFirebase : String
    val _state = MutableStateFlow<Result>(Result.Empty)
    val stateUI : StateFlow<Result> = _state

    var addValuesToOneNewUser = HashMap<String,String>()

    init {
        viewModelScope.async {
            tokenFirebase = emailRepository.generateNewTokenFCM()
        }
    }


    fun checkEmailIsVerifiead(){
        try {
            val result = checkUserAuthenticated()!!.isEmailVerified
            _state.value = Result.Success(result)
        }catch (exception : Exception){ }
    }

    fun checkUserAuthenticated(): FirebaseUser? {
        return emailRepository.userAuthenticated()
    }

    fun logout(){
        emailRepository.logout()
    }

    fun login(emailUser: String, password: String) {
        viewModelScope.async {
            _state.value = emailRepository.singEmailPassword(emailUser,password)
        }
    }

    fun forgotPassword(emailUser: String){
        val result = emailRepository.sendPasswordResetEmail(emailUser)
        _state.value = Result.Success(result)
    }

    fun register(uriImage:Uri ,email:String, password: String){
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

    fun createNewUser(uidUser: String,urlDaImagem: String,statusActivated : Boolean): Passageiro {
        addValuesToOneNewUser.put("uidUser",uidUser)
        addValuesToOneNewUser.put("urlDaFoto",urlDaImagem)
        addValuesToOneNewUser.put("token",tokenFirebase)
        return ResgitrationViewParams(addValuesToOneNewUser,statusActivated).create()

    }

}

sealed class Result{
    data class Success(val status: Boolean) : Result()
    data class Error(val exception: Exception) : Result()
    object Empty: Result()

}





