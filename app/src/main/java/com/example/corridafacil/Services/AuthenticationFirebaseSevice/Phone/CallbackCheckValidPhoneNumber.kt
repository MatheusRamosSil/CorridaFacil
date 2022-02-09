package com.example.corridafacil.Services.AuthenticationFirebaseSevice.Phone

import java.lang.Exception

interface CallbackCheckValidPhoneNumber {
    fun onSuccess(successful: Boolean)
    abstract fun onFailure(exception: Exception)


}