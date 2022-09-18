package com.example.corridafacil.domain.services.GoogleAutocompletePlacesService

import com.google.android.libraries.places.api.model.Place
import java.lang.Exception

interface GoogleAutocompletePlaceServiceImp {

    fun onSuccess(place: Place)
    fun getAdress(adress:String){}
    fun onError(status: Exception)

}
