package com.example.corridafacil.authenticationFirebase.repository

import android.app.Activity
import com.example.corridafacil.domain.services.AuthenticationFirebaseSevice.Phone.AuthenticationPhoneFirebaseService
import com.example.corridafacil.domain.services.AuthenticationFirebaseSevice.Phone.CallbackCheckValidPhoneNumber
import com.example.corridafacil.data.repository.auth.phone.PhoneRepository
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider

class PhoneRepositoryImpl(private val authenticationPhoneFirebaseService: AuthenticationPhoneFirebaseService):PhoneRepository{


    override fun startVerificationPhone(
        telefoneUsuario: String,
        activity: Activity,
        callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    ) {
        authenticationPhoneFirebaseService.startPhoneNumberVerification(telefoneUsuario,activity,callbacks)
    }

    override fun checkValidNumberPhoneWithSingPhone(
        credentials: PhoneAuthCredential,
        checkValidPhoneNumber: CallbackCheckValidPhoneNumber
    ) {
        authenticationPhoneFirebaseService.signInWithPhoneAuthCredential(credentials,checkValidPhoneNumber)
    }

    override fun resendVerificationCode(
        telefoneUsuario: String,
        activity: Activity,
        callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks,
        token: PhoneAuthProvider.ForceResendingToken?
    ) {
        authenticationPhoneFirebaseService.resendVerificationCode(telefoneUsuario,activity,callbacks,token)
    }

    override fun createCredentialsAuthPhoneNumber(verificationId: String, codeSMS: String): PhoneAuthCredential {
        return authenticationPhoneFirebaseService.createCredentialsAuthPhoneNumber(verificationId, codeSMS)
    }
}