package com.example.corridafacil.Services.AuthenticationFirebaseSevice

import com.example.corridafacil.Services.AuthenticationFirebaseSevice.Models.AuthPhone
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class AuthenticationPhoneFirebaseService( private val mCallbacksPhone: PhoneAuthProvider.OnVerificationStateChangedCallbacks ) {

    var firebaseAuth = FirebaseAuthentication.getInstanceFirebaseAuth()

        fun currentUser() = firebaseAuth.currentUser

        fun startPhoneNumberVerification(dadosDeAutenticacaoTelefone: AuthPhone){
            val options = PhoneAuthOptions.newBuilder(firebaseAuth)
                .setPhoneNumber(dadosDeAutenticacaoTelefone.telefoneDoUsuario) // Phone number to verify
                .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                .setCallbacks(mCallbacksPhone) // OnVerificationStateChangedCallbacks
                .setActivity(dadosDeAutenticacaoTelefone.activity)
                .build()
            PhoneAuthProvider.verifyPhoneNumber(options)
        }

        fun verifyPhoneNumberWithCode(verificationId: String, code: String): PhoneAuthCredential {
             val credential = PhoneAuthProvider.getCredential(verificationId!!, code)
            return credential
        }

        fun resendVerificationCode(dadosDeAutenticacaoTelefone: AuthPhone,
                                   token: PhoneAuthProvider.ForceResendingToken?
        ){
            val optionsBuilder = PhoneAuthOptions.newBuilder(firebaseAuth)
                .setPhoneNumber(dadosDeAutenticacaoTelefone.telefoneDoUsuario)       // Phone number to verify
                .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                .setActivity(dadosDeAutenticacaoTelefone.activity)                 // Activity (for callback binding)
                .setCallbacks(mCallbacksPhone)          // OnVerificationStateChangedCallbacks
            if (token != null) {
                optionsBuilder.setForceResendingToken(token) // callback's ForceResendingToken
            }
            PhoneAuthProvider.verifyPhoneNumber(optionsBuilder.build())
        }

}