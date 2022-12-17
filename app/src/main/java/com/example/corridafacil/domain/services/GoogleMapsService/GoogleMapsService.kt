package com.example.corridafacil.domain.services.GoogleMapsService

import android.annotation.SuppressLint
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.util.Log
import com.example.corridafacil.domain.services.GoogleMapsService.Models.MapApplication
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException


open class GoogleMapsService (private val mapApplication: MapApplication){

    private var lastKnownLocation: Location? = null
    private lateinit var locationCallback: LocationCallback

    val defaultLocation = LatLng(-33.8523341, 151.2106085)


    @SuppressLint("MissingPermission")
    suspend fun getDeviceLocation() = suspendCancellableCoroutine<LatLng>{ conti ->
        lateinit var myDeviceLocation : LatLng
        Log.i("Test device location","is a test location device")
        /*
         * Get the best and most recent location of the device, which may be null in   rare
         * cases when a location is not available.
         */
        try {
            if (mapApplication.locationPermissionGranted == true) {
                val locationResult = mapApplication.fusedLocationProviderClient?.lastLocation
                locationResult!!.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Set the map's camera position to the current location of the device.
                        lastKnownLocation = task.result
                        if (lastKnownLocation != null) {
                            myDeviceLocation = LatLng(lastKnownLocation!!.latitude, lastKnownLocation!!.longitude)
                            conti.resume(myDeviceLocation)
                            //googleMapsSeviceImp.onSucess(myDeviceLocation)
                            mapApplication.mMap.isMyLocationEnabled = true
                        }
                    } else {
                        mostrarLocalizacaoPadrao()
                    }
                }
            }
        } catch (e: Exception) {
            //googleMapsSeviceImp.onFailure(e.toString())
           conti.resumeWithException(e)
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

    @SuppressLint("MissingPermission")
    suspend fun onLocationChanged() = suspendCancellableCoroutine<Location> {

        val listenerLocation = object : LocationListener{
            override fun onLocationChanged(p0: Location) {
                Log.i("Changed Location", p0.latitude.toString() +","+p0.longitude.toString())
                it.resume(p0)
                it.isActive
            }

        }
        mapApplication.locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000,1.0f,listenerLocation)
    }

}




