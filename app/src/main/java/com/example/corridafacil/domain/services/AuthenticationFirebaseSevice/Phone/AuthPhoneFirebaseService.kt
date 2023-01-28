package com.example.corridafacil.domain.services.AuthenticationFirebaseSevice.Phone

import android.app.Activity
import android.util.Log
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import com.example.corridafacil.view.auth.viewModel.Result
import com.google.firebase.auth.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.tasks.await

class AuthPhoneFirebaseService @Inject constructor() : AuthPhoneImpl {

    private val auth = Firebase.auth

    override fun verifyPhoneNumber(
        phoneNumber: String,
        activity: Activity,
        callbackPhoneAuthProvider: PhoneAuthProvider.OnVerificationStateChangedCallbacks, ) {
        Log.w("Chamou", "verifyNumber")


        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)
            .setActivity(activity)
            .setCallbacks(callbackPhoneAuthProvider)
            .setTimeout(10L, TimeUnit.SECONDS)
            .build()

        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    override suspend fun validateCodeSMSWithPhoneAuthCredential(
        credential: PhoneAuthCredential,
    ): FirebaseUser? {

           return Firebase.auth.signInWithCredential(credential).await().user



        /*
        var _state = MutableStateFlow<Result>(Result.Empty)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(activity){ task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.w("Success login phone", "signInWithCredential:success")
                    _state.value = Result.Success(task.isSuccessful)
                    val user = task.result?.user
                } else {
                    // Sign in failed, display a message and update the UI
                    Log.w("Error login phone", "signInWithCredential:failure", task.exception)
                    task.exception?.let { _state.value = Result.Error(exception = it) }
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                    }
                    // Update UI
                }

            }

         return _state.value
    }

         */
    }
}