package com.example.corridafacil.Services.GoogleMapsService

import android.location.Location
import android.util.Log
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng

class GoogleMapsService{

    private var lastKnownLocation: Location? = null
    val defaultLocation = LatLng(-33.8523341, 151.2106085)

    fun getDeviceLocation(mapApplication:MapApplication) {
        Log.i("Test device location","is a test location device")
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            if (mapApplication.locationPermissionGranted == true) {
                val locationResult = mapApplication.fusedLocationProviderClient?.lastLocation
                locationResult.addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            // Set the map's camera position to the current location of the device.
                            lastKnownLocation = task.result
                            if (lastKnownLocation != null) {
                                val myDeviceLocation = LatLng(
                                    lastKnownLocation!!.latitude,
                                    lastKnownLocation!!.longitude)

                                mapApplication.mMap.isMyLocationEnabled = true
                               moverVisualizacaoParaALocazicaoDoDispositivo(myDeviceLocation,mapApplication)
                            }
                        } else {
                            mostrarLocalizacaoPadrao(mapApplication)
                        }
                    }
            }
        } catch (e: SecurityException) {

        }
    }

    private fun moverVisualizacaoParaALocazicaoDoDispositivo( myDeviceLocation:LatLng,
                                                              mapApplication: MapApplication){
        mapApplication.mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myDeviceLocation, 18f))
    }

    private fun mostrarLocalizacaoPadrao(mapApplication: MapApplication){
        mapApplication.map.moveCamera(
            CameraUpdateFactory
                .newLatLngZoom(defaultLocation, 15f))
        mapApplication.map.uiSettings?.isMyLocationButtonEnabled = false
    }
}


