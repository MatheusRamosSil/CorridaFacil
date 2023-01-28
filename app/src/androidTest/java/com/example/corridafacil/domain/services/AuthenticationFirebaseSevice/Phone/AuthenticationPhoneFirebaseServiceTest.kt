package com.example.corridafacil.domain.services.AuthenticationFirebaseSevice.Phone

import android.app.Activity
import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import io.mockk.mockk
import kotlinx.coroutines.runBlocking

import org.junit.After
import org.junit.Before
import org.junit.Test

class AuthenticationPhoneFirebaseServiceTest {

    @Before
    fun setUp() {
    }

    @Test
    fun verificando_numero_de_telefone(){
        val mockActivity = mockk<Activity>(relaxed = true)
        val result = MockAuthenticationPhoneFirbaseServiceImpl()

        val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verification without
                //     user action.
                Log.d(TAG, "onVerificationCompleted:$credential")

            }

            override fun onVerificationFailed(e: FirebaseException) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                Log.w(TAG, "onVerificationFailed", e)

                if (e is FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                } else if (e is FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                }

                // Show a message and update the UI
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.d(TAG, "onCodeSent:$verificationId")

            }
        }

        val phoneAuth = mockk<AuthenticationPhone>()
        phoneAuth.verifyPhoneNumber("+5587999690335",mockActivity,callbacks)

    }

    @After
    fun tearDown() {
    }
}

class MockAuthenticationPhoneFirbaseServiceImpl():AuthenticationPhone{
    private val instanceMockFireAuthentication = FirebaseAuth.getInstance()

    override fun verifyPhoneNumber(
        phoneNumber: String,
        activity: Activity,
        callbackPhoneAuthProvider: PhoneAuthProvider.OnVerificationStateChangedCallbacks, ) {
        val options = PhoneAuthOptions.newBuilder(instanceMockFireAuthentication)
            .setPhoneNumber(phoneNumber)
            .setActivity(activity)
            .setCallbacks(callbackPhoneAuthProvider)
            .build()

        PhoneAuthProvider.verifyPhoneNumber(options)
    }

}

interface AuthenticationPhone{
     fun verifyPhoneNumber(phoneNumber:String,activity :Activity, callbackPhoneAuthProvider: PhoneAuthProvider.OnVerificationStateChangedCallbacks)
}