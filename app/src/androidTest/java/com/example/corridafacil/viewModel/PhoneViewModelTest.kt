package com.example.corridafacil.viewModel

import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.corridafacil.data.repository.auth.phone.PhoneRepository
import com.example.corridafacil.domain.services.AuthenticationFirebaseSevice.Phone.AuthPhoneFirebaseService
import com.example.corridafacil.domain.services.AuthenticationFirebaseSevice.Phone.AuthPhoneImpl
import com.example.corridafacil.view.auth.ui.register.RegisteCodeOTPActivity
import com.example.corridafacil.view.auth.viewModel.PhoneViewModel
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PhoneViewModelTest{

    @Before
    fun setup(){

    }
    val callbackPhonetAuth = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks(){

        override fun onVerificationCompleted(p0: PhoneAuthCredential) {

        }

        override fun onVerificationFailed(p0: FirebaseException) {

        }

        override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
            super.onCodeSent(p0, p1)
            Log.w("oi", "code sent entrou")
        }
    }

    @Test
    fun verify_phone_number(){
        lateinit var authPhone : AuthPhoneImpl
        authPhone = AuthPhoneFirebaseService()
        val mockPhoneRepository = mockk<PhoneRepository>(relaxed = true)
        val viewModelPhone = PhoneViewModel(mockPhoneRepository, authPhone)
        val mockActivity = mockk<RegisteCodeOTPActivity>(relaxed = true)

        //viewModelPhone.verifyPhoneNumber("+5511123456789",mockActivity,callbackPhonetAuth)


    }
}