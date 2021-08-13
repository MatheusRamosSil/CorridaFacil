package com.example.corridafacil.Services.GoogleMapsService

import com.google.android.gms.maps.model.LatLng

interface GoogleMapsSeviceImp {

    fun onSucess(myDeviceLocation: LatLng)

    fun onFailure(menssage:String)
}