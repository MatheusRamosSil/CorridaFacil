package com.example.corridafacil.mapa.Utils.GPSManager

import android.content.Context
import android.location.LocationManager
import androidx.core.content.ContextCompat.getSystemService

fun Context.isLocationEnabled(): Boolean {
    var locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
    return locationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager!!.isProviderEnabled(
        LocationManager.NETWORK_PROVIDER)
}