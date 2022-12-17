package com.example.corridafacil.domain.services.AuthenticationFirebaseSevice.Phone

import android.app.Activity
import com.example.corridafacil.domain.services.AuthenticationFirebaseSevice.FirebaseAuthentication
import com.example.corridafacil.domain.services.AuthenticationFirebaseSevice.PhoneProviderUtil
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider

class AuthenticationPhoneFirebaseService: AuthenticationPhoneFirebase {

    override fun signInWithPhoneAuthCredential(
        credential: PhoneAuthCredential,
        checkValidPhoneNumber: CallbackCheckValidPhoneNumber
    ) {
        val firebaseAuth = FirebaseAuthentication.getInstanceFirebaseAuth()
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful){
                    checkValidPhoneNumber.onSuccess(task.isSuccessful)
                }else{
                    task?.exception?.let { checkValidPhoneNumber.onFailure(it) }
                }
            }
    }

    override fun startPhoneNumberVerification(telefoneUsuario: String, activity: Activity,
                                              callbackPhoneAuthProvider: PhoneAuthProvider.OnVerificationStateChangedCallbacks) {
        val options = PhoneProviderUtil.createPhoneAuthOptions(telefoneUsuario,activity, callbackPhoneAuthProvider).build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }


    override fun resendVerificationCode(
        telefoneUsuario: String,
        activity: Activity,
        callbackPhoneAuthProvider: PhoneAuthProvider.OnVerificationStateChangedCallbacks,
        token: PhoneAuthProvider.ForceResendingToken?
    ) {
        val optionsBuilder = PhoneProviderUtil.createPhoneAuthOptions(telefoneUsuario,activity,callbackPhoneAuthProvider)// OnVerificationStateChangedCallbacks
        if (token != null) {
            optionsBuilder.setForceResendingToken(token) // callback's ForceResendingToken
        }
        PhoneAuthProvider.verifyPhoneNumber(optionsBuilder.build())
    }


    override fun createCredentialsAuthPhoneNumber(verificationId: String, codeSMS : String):PhoneAuthCredential {
        return PhoneAuthProvider.getCredential(verificationId,codeSMS)
    }

}