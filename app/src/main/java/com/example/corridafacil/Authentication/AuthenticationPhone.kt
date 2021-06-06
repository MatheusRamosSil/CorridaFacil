package com.example.corridafacil.Authentication

import android.app.Activity
import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import java.util.concurrent.TimeUnit

class AuthenticationPhone : AppCompatActivity(){

    private  var mAuth = FirebaseAuth.getInstance()
    private lateinit var mCallbacks:PhoneAuthProvider.OnVerificationStateChangedCallbacks
    lateinit var mVerificationCode:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    fun startPhoneNumberVerification(telefoneUser: String?, mCallbacks:PhoneAuthProvider.OnVerificationStateChangedCallbacks) {
        var mAuth = FirebaseAuth.getInstance()
        val options = PhoneAuthOptions.newBuilder(mAuth)
            .setPhoneNumber(telefoneUser) // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setCallbacks(mCallbacks) // OnVerificationStateChangedCallbacks
            .setActivity(this)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    fun verifyPhoneNumberWithCode(verificationId: String, code: String){
        val credential = PhoneAuthProvider.getCredential(verificationId!!, code)
        autenticandoNumeroDeTelefoneComSign(credential)
    }

    fun autenticandoNumeroDeTelefoneComSign(credential: PhoneAuthCredential?) {
        var checkCredential = false
        mAuth.signInWithCredential(credential).addOnCompleteListener{ task ->
            if (task.isSuccessful) {
                val user = task.result.user
                checkCredential = true
            } else {
                if (task.exception is FirebaseAuthInvalidCredentialsException) {
                    // The verification code entered was invalid
                }

            }
        }
    }

}