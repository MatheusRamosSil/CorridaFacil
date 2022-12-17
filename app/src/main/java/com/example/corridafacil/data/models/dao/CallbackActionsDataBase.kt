package com.example.corridafacil.data.models.dao

import com.example.corridafacil.data.models.Passageiro
import java.lang.Exception

interface CallbackActionsDataBase{
    fun onSuccess(successful: Boolean)
    fun onReadDataUser(passageiro: Passageiro){}
    fun onFailure(cause: Exception)
}