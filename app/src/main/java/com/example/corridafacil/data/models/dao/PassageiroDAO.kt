package com.example.corridafacil.data.models.dao

import com.example.corridafacil.data.models.Passageiro

interface PassageiroDAO {
    suspend fun createNewPassageiro(passageiro: Passageiro):Boolean

    suspend fun updateUser(
        userUID: String?,
        userData: Map<String,String>):Boolean

    suspend fun readDataUser(
        userUID: String
    ): Passageiro

    fun disableUser(userUID: String, callbackActionsDataBase: CallbackActionsDataBase)
}