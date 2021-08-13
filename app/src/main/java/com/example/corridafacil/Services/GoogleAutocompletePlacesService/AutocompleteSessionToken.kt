package com.example.corridafacil.Services.GoogleAutocompletePlacesService

import com.google.android.libraries.places.api.model.AutocompleteSessionToken

class SessionToken {
    companion object{
        private val token by lazy{
            AutocompleteSessionToken.newInstance()
        }

        fun createNewToken() = token
    }
}