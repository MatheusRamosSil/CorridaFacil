package com.example.corridafacil.view.auth.viewModel

import android.annotation.SuppressLint
import android.app.Activity
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.corridafacil.domain.services.AuthenticationFirebaseSevice.Phone.CallbackCheckValidPhoneNumber
import com.example.corridafacil.data.repository.auth.phone.PhoneRepository
import com.example.corridafacil.domain.services.AuthenticationFirebaseSevice.Phone.AuthPhoneImpl
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@SuppressLint("StaticFieldLeak")
@HiltViewModel
class PhoneViewModel @Inject constructor(
    private val phoneRepository: PhoneRepository,
    private val authPhone: AuthPhoneImpl
) : ViewModel() {

    var verificationId = MutableLiveData<String>()
    var phoneAuthToken = MutableLiveData<PhoneAuthProvider.ForceResendingToken>()
    var statusLogin = MutableLiveData<Boolean>()
    var menssageErrorLogin = MutableLiveData<Exception>()
    val getCredentialsValidad = MutableLiveData<PhoneAuthCredential>()


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

    var uiState = mutableStateOf(RegisterCodeOtpState())
        private set


        val callbackPhonetAuth = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks(){

            override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                viewModelScope.launch {
                    validateCodeOTPWithSingInPhone(p0)
                }

            }

            override fun onVerificationFailed(p0: FirebaseException) {
                menssageErrorLogin.value = p0
            }

            override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
                super.onCodeSent(p0, p1)
                verificationId.value = p0
                phoneAuthToken.value = p1
                uiState.value = uiState.value.copy(p0)
                Log.w("Code sent", "onCodeSent:${uiState.component1().codeId}")
            }
        }


    
    
    fun verifyPhoneNumber(phoneNumber: String,
                          activity: Activity) {
        authPhone.verifyPhoneNumber(phoneNumber,activity,callbackPhonetAuth)
    }

    suspend fun validateCodeOTPWithSingInPhone(
        credential: PhoneAuthCredential,
    ): FirebaseUser? {
        return authPhone.validateCodeSMSWithPhoneAuthCredential(credential)
    }



}

data class RegisterCodeOtpState(
    val codeId: String = "",
    val credential: PhoneAuthCredential?= null,
)