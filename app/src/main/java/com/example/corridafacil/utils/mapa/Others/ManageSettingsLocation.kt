package com.example.corridafacil.utils.mapa.Others

import android.annotation.SuppressLint
import android.app.Activity
import android.content.IntentSender
import android.os.Looper
import android.util.Log
import com.example.corridafacil.domain.services.GoogleMapsService.Models.MapApplication
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task


class ManageSettingsLocation(private val mapApplication: MapApplication) {

    private val REQUEST_CHECK_SETTINGS = 10

    fun createLocationRequest(): LocationRequest {
        val locationRequest = LocationRequest.create().apply {
            interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            numUpdates = 1
            //smallestDisplacement = 20f
        }
        return locationRequest
    }

    fun checkManagerLocation(locationRequest: LocationRequest, locationCallback: LocationCallback){
        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)

        val client: SettingsClient = LocationServices.getSettingsClient(mapApplication.context)
        val task: Task<LocationSettingsResponse> = client.checkLocationSettings(builder.build())

        task.addOnSuccessListener {
            startLocationUpdates(locationRequest, locationCallback)
        }

        task.addOnFailureListener { exception ->
            if (exception is ResolvableApiException){
                try {
                    exception.startResolutionForResult(
                        mapApplication.context as Activity,
                        REQUEST_CHECK_SETTINGS)
                } catch (sendEx: IntentSender.SendIntentException) {
                    // Ignore the error.
                }
            }
        }

    }

    @SuppressLint("MissingPermission", "LongLogTag")
    fun startLocationUpdates(locationRequest: LocationRequest, locationCallback: LocationCallback) {
        try {
            Log.i("Location device", "funtionc2")
            mapApplication.fusedLocationProviderClient.requestLocationUpdates(locationRequest,
                locationCallback,
                Looper.getMainLooper())
        }catch (exception: Exception){
            Log.w("Error updates locations device", exception.message.toString())
        }

    }

    @SuppressLint("LongLogTag")
    fun stopLocationUpdates(locationCallback: LocationCallback){
        try {
            mapApplication.fusedLocationProviderClient.removeLocationUpdates(locationCallback)
        }catch (exception:Exception){
            Log.w("Error in stop updates location device", exception.message.toString())
        }

    }
}