package com.example.corridafacil.domain.services.AuthenticationFirebaseSevice.Models

import android.app.Activity

class AuthPhone{
    lateinit var telefoneDoUsuario:String
    lateinit var activity: Activity

    companion object Factory{
        fun create(): AuthPhone = AuthPhone()
    }

}