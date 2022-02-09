package com.example.corridafacil.Services.AuthenticationFirebaseSevice.Email

import java.lang.Exception

interface AuthFirebaseEmailCallbackImp {
    fun getUidUser(uid:String)

    fun onSuccess()

    fun onFailure(menssage: Exception?)
}