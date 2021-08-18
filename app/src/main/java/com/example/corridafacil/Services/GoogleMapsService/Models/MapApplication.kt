package com.example.corridafacil.Services.GoogleMapsService.Models

import android.content.Context
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.GoogleMap

class MapApplication {

     var locationPermissionGranted: Boolean? = false
     lateinit var fusedLocationProviderClient: FusedLocationProviderClient
     lateinit var mMap: GoogleMap
     lateinit var map: GoogleMap
     lateinit var context: Context

    companion object Factory{
        fun create(): MapApplication = MapApplication()
    }
}