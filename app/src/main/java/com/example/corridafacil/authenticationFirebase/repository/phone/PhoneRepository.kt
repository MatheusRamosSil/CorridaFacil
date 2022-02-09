package com.example.corridafacil.authenticationFirebase.repository.phone

import android.app.Activity
import com.example.corridafacil.Services.AuthenticationFirebaseSevice.Phone.CallbackCheckValidPhoneNumber
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider

interface PhoneRepository {

    fun startVerificationPhone(telefoneUsuario:String
                               ,activity: Activity
                               ,callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks)

    fun checkValidNumberPhoneWithSingPhone(credentials: PhoneAuthCredential, checkValidPhoneNumber: CallbackCheckValidPhoneNumber)

    fun resendVerificationCode(telefoneUsuario:String
                               ,activity: Activity
                               ,callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
                               ,token: PhoneAuthProvider.ForceResendingToken?)

    fun createCredentialsAuthPhoneNumber(verificationId : String, codeSMS : String): PhoneAuthCredential


}