package com.example.corridafacil.mapa.Utils.Others

import android.annotation.SuppressLint
import android.location.Location
import android.os.Looper
import com.example.corridafacil.Services.GoogleMapsService.Models.MapApplication
import com.google.android.gms.location.*

class ManageSettingsLocation(private val mapApplication: MapApplication) {

    fun createLocationRequest(): LocationRequest {
        val locationRequest = LocationRequest.create().apply {
            interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            numUpdates = 1
        }
        return locationRequest
    }


    @SuppressLint("MissingPermission")
    fun startLocationUpdates(locationRequest: LocationRequest, locationCallback: LocationCallback) {
       mapApplication.fusedLocationProviderClient.requestLocationUpdates(locationRequest,
            locationCallback,
            Looper.getMainLooper())
    }

    fun stopLocationUpdates(locationCallback: LocationCallback){
        mapApplication.fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }
}