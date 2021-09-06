package com.example.corridafacil.Services.GoogleMapsService

import android.location.Location
import android.util.Log
import com.example.corridafacil.Services.GoogleMapsService.Models.MapApplication
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class GoogleMapsService (private val mapApplication: MapApplication){

    private var lastKnownLocation: Location? = null
    val defaultLocation = LatLng(-33.8523341, 151.2106085)


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

    fun removerMarcadorEmUmaLista(key: String, hashMapMarker: HashMap<String,Marker>  ){
            Log.i("Remove list", hashMapMarker.keys.toString())
            val marker: Marker? = hashMapMarker.get(key)
            marker?.remove()
            hashMapMarker.remove(key)
    }

    fun adicionarNovoPontoNoMapa(novoPonto:LatLng): Marker{
        return mapApplication.mMap.addMarker(MarkerOptions().position(novoPonto))
    }

    fun removerMarcardorDoMapa(marker: Marker){
        Log.i("Marker Location", marker.position.toString())
        marker.remove()
    }

    fun moverVisualizacaoParaALocazicaoDoDispositivo( tamanhoDaVisualizacao:LatLngBounds){
        mapApplication.mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(tamanhoDaVisualizacao,50))
    }

    private fun mostrarLocalizacaoPadrao() {
        mapApplication.map.moveCamera(
            CameraUpdateFactory
                .newLatLngZoom(defaultLocation, 15f))
        mapApplication.map.uiSettings?.isMyLocationButtonEnabled = false
    }


    fun marcarVariosPontosNoMapa(multiplePoints: ArrayList<LatLng>): LatLngBounds {
        val limitesDaVisulizacao = LatLngBounds.builder()
        for (pontos:LatLng in multiplePoints){
            limitesDaVisulizacao.include(pontos)
            mapApplication.mMap.addMarker(MarkerOptions().position(pontos))
        }
        val tamanhoDaVisualizacao: LatLngBounds = limitesDaVisulizacao.build()
        return tamanhoDaVisualizacao

    }

    fun cleaningMap() {
       mapApplication.mMap.clear()
    }

}


