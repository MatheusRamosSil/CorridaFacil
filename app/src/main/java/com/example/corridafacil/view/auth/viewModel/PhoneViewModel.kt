package com.example.corridafacil.view.auth.viewModel

import android.annotation.SuppressLint
import android.app.Activity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.corridafacil.domain.services.AuthenticationFirebaseSevice.Phone.CallbackCheckValidPhoneNumber
import com.example.corridafacil.data.repository.auth.phone.PhoneRepository
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import java.lang.Exception

@SuppressLint("StaticFieldLeak")
class PhoneViewModel (private val phoneRepository: PhoneRepository) : ViewModel() {

    var verificationId = MutableLiveData<String>()
    var phoneAuthToken = MutableLiveData<PhoneAuthProvider.ForceResendingToken>()
    var statusLogin = MutableLiveData<Boolean>()
    var menssageErrorLogin = MutableLiveData<Exception>()
    val getCredentialsValidad = MutableLiveData<PhoneAuthCredential>()

    val callbackPhonetAuth = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks(){

        override fun onVerificationCompleted(p0: PhoneAuthCredential) {
            getCredentialsValidad.value = p0
        }

        override fun onVerificationFailed(p0: FirebaseException) {
            menssageErrorLogin.value = p0
        }

        override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
            super.onCodeSent(p0, p1)
            verificationId.value = p0
            phoneAuthToken.value = p1
        }
    }

    fun criarNovasCredenciaisDeAutencacaoTelefone(codeSMS : String): PhoneAuthCredential? {

        return verificationId.value?.let { phoneRepository.createCredentialsAuthPhoneNumber(it, codeSMS) }
    }

    fun iniciarVerificacaoNumeroTelefone(telefoneDoUsuario:String,activity: Activity){
        phoneRepository.startVerificationPhone(telefoneDoUsuario,activity,callbackPhonetAuth)
    }

    fun reenviarCodigoDeVerificacao(telefoneDoUsuario: String,activity: Activity){
        phoneRepository.resendVerificationCode(telefoneDoUsuario,activity,callbackPhonetAuth,phoneAuthToken.value)
    }

    fun verificarSeNumeroDeTelefoneValidoComLoginPhone(credential: PhoneAuthCredential) {
        phoneRepository.checkValidNumberPhoneWithSingPhone(credential, object : CallbackCheckValidPhoneNumber {
            override fun onSuccess(successful: Boolean) {
                statusLogin.value = successful
            }

            override fun onFailure(exception: Exception) {
                menssageErrorLogin.value = exception
            }
        })

    }

}