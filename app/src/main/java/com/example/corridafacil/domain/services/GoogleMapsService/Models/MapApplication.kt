package com.example.corridafacil.domain.services.GoogleMapsService.Models

import android.content.Context
import android.location.LocationManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.GoogleMap

open class MapApplication (){

     var locationPermissionGranted: Boolean? = false
     lateinit var fusedLocationProviderClient: FusedLocationProviderClient
     lateinit var mMap: GoogleMap
     lateinit var map: GoogleMap
     lateinit var context :Context
     lateinit var locationManager:LocationManager

    companion object Factory{
        fun create(): MapApplication = MapApplication()
    }
}