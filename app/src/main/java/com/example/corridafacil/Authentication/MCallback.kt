package com.example.corridafacil.Authentication

import android.content.ContentValues
import android.util.Log
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider

class MCallback {
    private lateinit var mCallbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks

    fun initMCallbacks () {
        mCallbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
            override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
                super.onCodeSent(p0, p1)
                getCodeSmS(p0)
            }
            override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                val code = p0.smsCode
                code?.let {getIt(it)}
            }
            override fun onVerificationFailed(p0: FirebaseException) {
                Log.d(ContentValues.TAG, "Teste de saida: "+p0)
                getFailedMensagen(p0.toString())
            }
        }
    }

    fun getCodeSmS( code:String) :String{
        return code
    }
    fun getIt(it:String):String{
        return it
    }
    fun getFailedMensagen(error:String):String{
        return error
    }

}