package com.example.corridafacil.mapa.Utils.Others

import android.content.Context
import android.location.LocationManager
import com.example.corridafacil.mapa.Utils.ContantsMaps.GOOGLE_MAPS_API_KEY
import com.google.android.libraries.places.api.Places

fun Context.isLocationEnabled(): Boolean {
    var locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
    return locationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager!!.isProviderEnabled(
        LocationManager.NETWORK_PROVIDER)
}


