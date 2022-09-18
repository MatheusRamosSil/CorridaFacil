package com.example.corridafacil.domain.services.AuthenticationFirebaseSevice.Phone

import java.lang.Exception

interface CallbackCheckValidPhoneNumber {
    fun onSuccess(successful: Boolean)
    abstract fun onFailure(exception: Exception)


}