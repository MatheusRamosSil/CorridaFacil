package com.example.corridafacil.models.dao

import com.example.corridafacil.models.Passageiro

interface PassageiroDAO {
    suspend fun createNewPassageiro(passageiro: Passageiro):Boolean

    suspend fun updateUser(
        userUID: String?,
        userData: Map<String,String>):Boolean

    fun readDataUser(
        userUID: String,
        callback: CallbackActionsDataBase
    )

    fun disableUser(userUID: String, callbackActionsDataBase: CallbackActionsDataBase)
}