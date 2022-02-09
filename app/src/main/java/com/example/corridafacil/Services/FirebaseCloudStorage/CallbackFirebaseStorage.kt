package com.example.corridafacil.Services.FirebaseCloudStorage

interface CallbackFirebaseStorage {

    fun pegarAUrlAposUplaod(url: String)
    fun onFailure(exception: Exception)

}