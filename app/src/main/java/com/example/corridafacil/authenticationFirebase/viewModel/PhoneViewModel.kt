package com.example.corridafacil.authenticationFirebase.viewModel

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import com.example.corridafacil.Services.AuthenticationFirebaseSevice.Models.AuthPhone
import com.example.corridafacil.authenticationFirebase.Repository.PhoneRepository
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider

@SuppressLint("StaticFieldLeak")
class PhoneViewModel (private val phoneRepository: PhoneRepository) : ViewModel() {

    lateinit var code: String
    lateinit var phoneAuthToken: PhoneAuthProvider.ForceResendingToken
    lateinit var smsCode:String
    var authPhone = AuthPhone.create()

    fun startPhoneNumberVerification(){
         phoneRepository.startPhoneNumberVerification(authPhone)
    }

    fun verifyPhoneNumberWithCode():PhoneAuthCredential{
        val credential = phoneRepository.verifyPhoneNumberWithCode(code,smsCode)
        return credential
    }

    fun resendVerificationCode(){
        phoneRepository.resendVerificationCode(authPhone,phoneAuthToken)
    }
}