package com.example.corridafacil.authenticationFirebase.Repository

import com.example.corridafacil.Services.AuthenticationFirebaseSevice.AuthenticationPhoneFirebaseService
import com.example.corridafacil.Services.AuthenticationFirebaseSevice.Models.AuthPhone
import com.google.firebase.auth.PhoneAuthProvider

class PhoneRepository(private val authenticationPhoneFirebaseService: AuthenticationPhoneFirebaseService){

    fun startPhoneNumberVerification(authPhone: AuthPhone) =
           authenticationPhoneFirebaseService.startPhoneNumberVerification(authPhone)

    fun verifyPhoneNumberWithCode(verificationId: String, code: String) =
        authenticationPhoneFirebaseService.verifyPhoneNumberWithCode(verificationId,code)

    fun resendVerificationCode(dadosDeAutenticacaoTelefone: AuthPhone, token: PhoneAuthProvider.ForceResendingToken?) =
        authenticationPhoneFirebaseService.resendVerificationCode(dadosDeAutenticacaoTelefone,token)
}