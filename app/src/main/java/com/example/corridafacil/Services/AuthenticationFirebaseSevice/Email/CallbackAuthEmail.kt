package com.example.corridafacil.Services.AuthenticationFirebaseSevice.Email

interface CallbackAuthEmail {
    fun onSuccess(successful: Boolean)
    fun getUidUser(uid: String){}
    fun onFailure(exception: Exception)
}