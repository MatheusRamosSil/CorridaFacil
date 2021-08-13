package com.example.corridafacil.Services.GoogleAutocompletePlacesService

import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.model.Place

interface GoogleAutocompletePlaceServiceImp {

    fun onSuccess(place: Place)
    fun onError(status: Status)

}
