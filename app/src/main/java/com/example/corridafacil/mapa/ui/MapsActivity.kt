package com.example.corridafacil.mapa.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.example.corridafacil.Components.Alerts.exibirAlertaDeCofiguracoesDeGPS
import com.example.corridafacil.mapa.Utils.permissions.getLocationPermission
import com.example.corridafacil.mapa.Utils.permissions.hasLocationPermissions
import com.example.corridafacil.mapa.repository.MapRepository
import com.example.corridafacil.mapa.viewModel.MapViewModelFactory
import com.example.corridafacil.mapa.viewModel.MapsViewModel
import com.example.corridafacil.R
import com.example.corridafacil.Services.GoogleMapsService.GoogleMapsService
import com.example.corridafacil.Services.GoogleMapsService.MapApplication
import com.example.corridafacil.databinding.ActivityMapsBinding
import com.example.corridafacil.mapa.Utils.GPSManager.isLocationEnabled
import com.example.corridafacil.mapa.Utils.permissions.Constantes.PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
import com.google.android.gms.location.*

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private var locationPermissionGranted = false
    private lateinit var binding: ActivityMapsBinding
    lateinit var mapViewModel: MapsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mapViewModel =
            ViewModelProvider(this, MapViewModelFactory(
                MapRepository(
                    GoogleMapsService())
            )).get(MapsViewModel::class.java)
        binding.viewmodel = mapViewModel

        // Incializa a variaval de localização do dispositivo(fusedLocationProviderClient)
        binding.viewmodel?.mapApplication?.fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        inicilizarFragmentMap()
    }

    private fun inicilizarFragmentMap(){
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onStart() {
        checkGPSEnabled()
        super.onStart()
    }

    private fun checkGPSEnabled(){
        if (!isLocationEnabled()){
            exibirAlertaDeCofiguracoesDeGPS()
        }
    }

    override fun onResume() {
        inicilizarFragmentMap()
        super.onResume()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        binding.viewmodel?.mapApplication?.mMap = googleMap
        binding.viewmodel?.mapApplication?.locationPermissionGranted = getLocationPermission()
        binding.viewmodel?.getDeviceLocation()
    }

    // [START maps_current_place_on_request_permissions_result]
    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>,
                                            grantResults: IntArray) {
        locationPermissionGranted = false
        when (requestCode) {
            PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION -> {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    locationPermissionGranted = true
                }
            }
        }

    }
    // [END maps_current_place_on_request_permissions_result]
}