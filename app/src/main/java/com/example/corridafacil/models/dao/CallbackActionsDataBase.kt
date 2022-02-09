package com.example.corridafacil.models.dao

import com.example.corridafacil.models.Passageiro
import java.lang.Exception

interface CallbackActionsDataBase{
    fun onSuccess(successful: Boolean)
    fun onReadDataUser(passageiro: Passageiro){}
    fun onFailure(cause: Exception)
}