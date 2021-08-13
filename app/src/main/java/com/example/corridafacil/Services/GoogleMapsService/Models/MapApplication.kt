package com.example.corridafacil.Services.GoogleMapsService.Models

import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.GoogleMap

class MapApplication {

     var locationPermissionGranted: Boolean? = false
     lateinit var fusedLocationProviderClient: FusedLocationProviderClient
     lateinit var mMap: GoogleMap
     lateinit var map: GoogleMap

    companion object Factory{
        fun create(): MapApplication = MapApplication()
    }
}