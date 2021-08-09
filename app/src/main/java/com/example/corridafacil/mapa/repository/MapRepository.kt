package com.example.corridafacil.mapa.repository

import com.example.corridafacil.Services.GoogleMapsService.GoogleMapsService
import com.example.corridafacil.Services.GoogleMapsService.MapApplication

class MapRepository(private var googleMapsService: GoogleMapsService) {

    fun getLocationDevice(mapApplication: MapApplication){
       googleMapsService.getDeviceLocation(mapApplication)
    }

}
