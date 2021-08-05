package com.example.corridafacil.Services.AuthenticationFirebaseSevice

import com.google.firebase.auth.FirebaseAuth

class FirebaseAuthentication {
    companion object{
        private val firebaseAuth: FirebaseAuth by lazy {
            FirebaseAuth.getInstance()

        }
        fun getInstanceFirebaseAuth():FirebaseAuth{
            return firebaseAuth
        }

    }

}