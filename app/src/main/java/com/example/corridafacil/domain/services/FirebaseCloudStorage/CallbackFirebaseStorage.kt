package com.example.corridafacil.domain.services.FirebaseCloudStorage

interface CallbackFirebaseStorage {

    fun pegarAUrlAposUplaod(url: String)
    fun onFailure(exception: Exception)

}