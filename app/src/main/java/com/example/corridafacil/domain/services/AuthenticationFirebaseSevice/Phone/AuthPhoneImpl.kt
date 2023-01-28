package com.example.corridafacil.domain.services.AuthenticationFirebaseSevice.Phone

import android.app.Activity
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.example.corridafacil.view.auth.viewModel.Result
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

interface AuthPhoneImpl {
    fun verifyPhoneNumber(phoneNumber:String, activity : Activity, callbackPhoneAuthProvider: PhoneAuthProvider.OnVerificationStateChangedCallbacks)
    suspend fun validateCodeSMSWithPhoneAuthCredential(credential: PhoneAuthCredential): FirebaseUser?
}