package com.example.corridafacil.Services.AuthenticationFirebaseSevice

import android.content.ContentValues
import android.util.Log
import com.example.corridafacil.Services.AuthenticationFirebaseSevice.Models.AuthEmail

class AuthenticationEmailFirebase {
    var firebaseAuth = FirebaseAuthentication.getInstanceFirebaseAuth()

    companion object Factory{
        fun getInstance():AuthenticationEmailFirebase = AuthenticationEmailFirebase()
    }

    fun currentUser() = firebaseAuth.currentUser

    fun logout() = firebaseAuth.signOut()


    fun register(callbackImp: AuthFirebaseEmailCallbackImp, authEmail: AuthEmail) {
        firebaseAuth.createUserWithEmailAndPassword(authEmail.email,authEmail.password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful){
                    callbackImp.getUidUser(firebaseAuth.currentUser.uid)
                    callbackImp.onSuccess()
                    Log.i("TESTE UID", "my uid test :"+ firebaseAuth.currentUser.uid)
                    sendEmailVerification()
                }else{
                    // Adicionar exception de falha
                    Log.w(ContentValues.TAG, "createUserWithEmail:failure", task.getException())
                    callbackImp.onFailure(task.getException())
                }
            }

    }

    fun loginEmail(callbackImp: AuthFirebaseEmailCallbackImp, authEmail: AuthEmail){
        firebaseAuth.signInWithEmailAndPassword(authEmail.email,authEmail.password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful){
                    callbackImp.getUidUser(firebaseAuth.currentUser.uid)
                    callbackImp.onSuccess()
                Log.i("SUCCESS", "check email ok")
                }else{
                    Log.w(ContentValues.TAG, "signInWithEmail:failure", task.exception)
                    callbackImp.onFailure(task.exception)
                }
            }
    }

    fun sendEmailVerification() {
        val user = firebaseAuth.currentUser!!
        user.sendEmailVerification()
            .addOnCompleteListener { task ->

            }
    }

}