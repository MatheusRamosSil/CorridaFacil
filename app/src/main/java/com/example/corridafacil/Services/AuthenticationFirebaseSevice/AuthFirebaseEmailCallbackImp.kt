package com.example.corridafacil.Services.AuthenticationFirebaseSevice

import java.lang.Exception

interface AuthFirebaseEmailCallbackImp {
    fun getUidUser(uid:String)

    fun onSuccess()

    fun onFailure(menssage: Exception?)
}