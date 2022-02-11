package com.example.corridafacil.Services.GoogleMapsService

import android.annotation.SuppressLint
import android.location.Location
import android.util.Log
import com.example.corridafacil.Services.GoogleMapsService.Models.MapApplication
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.*
import kotlin.collections.HashMap


open class GoogleMapsService (private val mapApplication: MapApplication){

    private var lastKnownLocation: Location? = null
    val defaultLocation = LatLng(-33.8523341, 151.2106085)


    @SuppressLint("MissingPermission")
    fun getDeviceLocation(googleMapsSeviceImp: GoogleMapsSeviceImp) {
        Log.i("Test device location","is a test location device")
        /*
         * Get the best and most recent location of the device, which may be null in   rare
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
                            val myDeviceLocation = LatLng(lastKnownLocation!!.latitude, lastKnownLocation!!.longitude)
                            googleMapsSeviceImp.onSucess(myDeviceLocation)
                            mapApplication.mMap.isMyLocationEnabled = true
                        }
                    } else {
                        mostrarLocalizacaoPadrao()
                    }
                }
            }
        } catch (e: SecurityException) {
            googleMapsSeviceImp.onFailure(e.toString())
        }
    }

    fun removerMarcadorEmUmaLista(key: String, hashMapMarker: HashMap<String?,Marker>  ){
            Log.i("Remove list", hashMapMarker.keys.toString())
            val marker: Marker? = hashMapMarker.get(key)
            marker?.remove()
            hashMapMarker.remove(key)
    }

    @SuppressLint("MissingPermission")
    fun addMarkerInLocationDevice(deviceLocation:LatLng){
        mapApplication.mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(deviceLocation,18f))
        mapApplication.mMap.isMyLocationEnabled = true
    }

    fun adicionarNovoPontoNoMapa(novoPonto:LatLng): Marker{
        return mapApplication.mMap.addMarker(MarkerOptions().position(novoPonto))
    }

    fun removerMarcardorDoMapa(marker: Marker?){
        Log.i("Marker Location", marker?.position.toString())
        marker?.remove()
    }

    fun moverVisualizacaoParaALocazicaoDoDispositivo( tamanhoDaVisualizacao:LatLngBounds){
        mapApplication.mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(tamanhoDaVisualizacao, 10))
    }

    private fun mostrarLocalizacaoPadrao() {
        mapApplication.map.moveCamera(
            CameraUpdateFactory
                .newLatLngZoom(defaultLocation, 15f))
        mapApplication.map.uiSettings?.isMyLocationButtonEnabled = false
    }

    fun cleaningMap() {
       mapApplication.mMap.clear()
    }
}




