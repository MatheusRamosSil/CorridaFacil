package com.example.corridafacil.Services.GoogleAutocompletePlacesService

import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.model.Place
import java.lang.Exception

interface GoogleAutocompletePlaceServiceImp {

    fun onSuccess(place: Place)
    fun getAdress(adress:String){}
    fun onError(status: Exception)

}
