package com.example.corridafacil.domain.services.AuthenticationFirebaseSevice

import android.app.Activity
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

object PhoneProviderUtil {

    fun createPhoneAuthOptions(
        telefone: String,
        activity: Activity,
        callbackPhoneAuthProvider: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    ): PhoneAuthOptions.Builder {
        val firebaseAuth = FirebaseAuthentication.getInstanceFirebaseAuth()
        val options = PhoneAuthOptions.newBuilder(firebaseAuth)
            .setPhoneNumber(telefone)
            .setTimeout(30L, TimeUnit.SECONDS)
            .setActivity(activity)
            .setCallbacks(callbackPhoneAuthProvider)

        return options

    }
}