package com.example.corridafacil.domain.services.GoogleMapsService

import com.google.android.gms.maps.model.LatLng

interface GoogleMapsSeviceImp {

    fun onSucess(myDeviceLocation: LatLng)

    fun onFailure(menssage:String)
}