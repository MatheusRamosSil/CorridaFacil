package com.example.corridafacil.Services.AuthenticationFirebaseSevice.Phone

import android.app.Activity
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider

interface AuthenticationPhoneFirebase {



    fun signInWithPhoneAuthCredential(
        credential: PhoneAuthCredential,
        checkValidPhoneNumber: CallbackCheckValidPhoneNumber
    )

    fun startPhoneNumberVerification(telefoneUsuario: String,
                                     activity: Activity,
                                     callbackPhoneAuthProvider: PhoneAuthProvider.OnVerificationStateChangedCallbacks)


    fun resendVerificationCode(telefoneUsuario: String,
                               activity: Activity,
                               callbackPhoneAuthProvider: PhoneAuthProvider.OnVerificationStateChangedCallbacks,
                               token: PhoneAuthProvider.ForceResendingToken?,
    )

    fun createCredentialsAuthPhoneNumber(verificationId : String, codeSMS : String): PhoneAuthCredential


}