package com.example.corridafacil.Authentication

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import java.util.concurrent.TimeUnit

class AuthenticationPhone : AppCompatActivity(){

    private  var mAuth = FirebaseAuth.getInstance()
    private lateinit var mVerificationId:String
    private lateinit var mCallbacks:PhoneAuthProvider.OnVerificationStateChangedCallbacks
    lateinit var mVerificationCode:String

    private val falhaNaVerificaacoDoTelefone = "Por favor digite novavemnte seu numero de telefone"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initilizedMCallbacks()

    }

    fun initilizedMCallbacks(): PhoneAuthProvider.OnVerificationStateChangedCallbacks {
        mCallbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
            override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
                super.onCodeSent(p0, p1)
                mVerificationCode = p0
            }
            override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                val code = p0.smsCode
                code?.let { verifyPhoneNumberWithCode(mVerificationCode,it) }
            }
            override fun onVerificationFailed(p0: FirebaseException) {
                Log.d(ContentValues.TAG, "Teste de saida: "+p0)
            }
        }
        return this.mCallbacks
    }

    fun startPhoneNumberVerification(telefoneUser: String?, activity: Activity) {
        var mAuth = FirebaseAuth.getInstance()
        val options = PhoneAuthOptions.newBuilder(mAuth)
            .setPhoneNumber(telefoneUser) // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setCallbacks(initilizedMCallbacks()) // OnVerificationStateChangedCallbacks
            .setActivity(activity)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    fun verifyPhoneNumberWithCode(verificationId: String?, code: String?) {
        // [START verify_with_code]
        val credential = PhoneAuthProvider.getCredential(verificationId, code)
        signInWithPhoneAuthCredential(credential)
    }

    fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential?){
        mAuth.signInWithCredential(credential).addOnCompleteListener{ task ->
            if (task.isSuccessful) {
                val user = task.result.user
            } else {
                if (task.exception is FirebaseAuthInvalidCredentialsException) {
                    // The verification code entered was invalid
                }

            }
        }

    }

}