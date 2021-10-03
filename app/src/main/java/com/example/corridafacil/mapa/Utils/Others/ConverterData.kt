package com.example.corridafacil.mapa.Utils.Others

import com.google.android.gms.maps.model.LatLng

class ConvertData {
    fun latLngToString(location: LatLng?): String {
        val newLocationString = location?.latitude.toString()+","+location?.longitude.toString()
        return newLocationString
    }
}