package com.example.corridafacil.mapa.viewModel

import android.location.Location
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.corridafacil.Services.GoogleMapsService.MapApplication
import com.example.corridafacil.mapa.Utils.permissions.getLocationPermission
import com.example.corridafacil.mapa.repository.MapRepository
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng

class MapsViewModel(private val mapRepository: MapRepository) : ViewModel(){
    var mapApplication = MapApplication.create()

    fun getDeviceLocation() {
      mapRepository.getLocationDevice(mapApplication)
    }
}
