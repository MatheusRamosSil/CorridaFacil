package com.example.corridafacil.mapa.ui

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.corridafacil.Components.Alerts.exibirAlertaDeCofiguracoesDeGPS
import com.example.corridafacil.R
import com.example.corridafacil.Services.GoogleAutocompletePlacesService.GoogleAutocompletePlaceService
import com.example.corridafacil.Services.GoogleAutocompletePlacesService.Models.PlacesApplication
import com.example.corridafacil.Services.GoogleMapsService.GoogleMapsService
import com.example.corridafacil.Services.GoogleMapsService.Models.MapApplication
import com.example.corridafacil.databinding.ActivityMapsBinding
import com.example.corridafacil.mapa.Utils.ContantsMaps.GOOGLE_MAPS_API_KEY
import com.example.corridafacil.mapa.Utils.Others.isLocationEnabled
import com.example.corridafacil.mapa.Utils.permissions.Constantes.PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
import com.example.corridafacil.mapa.Utils.permissions.getLocationPermission
import com.example.corridafacil.mapa.repository.MapRepository
import com.example.corridafacil.mapa.viewModel.MapViewModelFactory
import com.example.corridafacil.mapa.viewModel.MapsViewModel
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.widget.AutocompleteSupportFragment


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private var locationPermissionGranted = false
    private lateinit var binding: ActivityMapsBinding
    lateinit var mapViewModel: MapsViewModel
    var mapApplication = MapApplication.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Iniciliza o autocomplete fragment
        val autocompleteSupportFragment = (supportFragmentManager.findFragmentById(R.id.autocomplete_fragment)
                                           as AutocompleteSupportFragment?)!!

        val placesApplication = PlacesApplication(this)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        mapViewModel =
            ViewModelProvider(this, MapViewModelFactory(
                MapRepository(
                    GoogleMapsService(mapApplication),
                    GoogleAutocompletePlaceService(autocompleteSupportFragment,placesApplication)
                )
            )).get(MapsViewModel::class.java)
        binding.viewmodel = mapViewModel

        // Incializa a variaval de localização do dispositivo(fusedLocationProviderClient)
        mapApplication.fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
    }



    override fun onStart() {
        checkGPSEnabled()
        super.onStart()
    }

    override fun onResume() {
        binding.viewmodel?.getDeviceLocation()
        super.onResume()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mapApplication.mMap=googleMap
        mapApplication.locationPermissionGranted = getLocationPermission()
        binding.viewmodel?.getDeviceLocation()
        inicilizeFramentAutocompletePlaces()
    }

    private fun inicilizeFramentAutocompletePlaces(){
        binding.viewmodel?.inicilizarAutocompletePlaces()
    }

    private fun initializePlaces() = Places.initialize(applicationContext, GOOGLE_MAPS_API_KEY)

    private fun checkGPSEnabled(){
        if (!isLocationEnabled()){
            exibirAlertaDeCofiguracoesDeGPS()
        }
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