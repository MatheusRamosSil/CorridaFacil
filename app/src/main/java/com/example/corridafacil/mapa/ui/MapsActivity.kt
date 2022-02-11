package com.example.corridafacil.mapa.ui

import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.corridafacil.Components.Alerts.exibirAlertaDeCofiguracoesDeGPS
import com.example.corridafacil.R
import com.example.corridafacil.Services.DirectionsRoutes.Retrofit.DirectionsRoutesServices
import com.example.corridafacil.Services.GoogleAutocompletePlacesService.GoogleAutocompletePlaceService
import com.example.corridafacil.Services.GoogleAutocompletePlacesService.Models.PlacesApplication
import com.example.corridafacil.Services.GoogleMapsService.GoogleMapsService
import com.example.corridafacil.Services.GoogleMapsService.Models.MapApplication
import com.example.corridafacil.models.Geofire.GeofireInFirebase
import com.example.corridafacil.databinding.ActivityMapsBinding
import com.example.corridafacil.mapa.Utils.Others.ManageSettingsLocation
import com.example.corridafacil.mapa.Utils.Others.isLocationEnabled
import com.example.corridafacil.mapa.Utils.permissions.Constantes.PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
import com.example.corridafacil.mapa.Utils.permissions.getLocationPermission
import com.example.corridafacil.mapa.repository.MapRepositoryImpl
import com.example.corridafacil.mapa.viewModel.MapViewModelFactory
import com.example.corridafacil.mapa.viewModel.MapsViewModel
import com.example.corridafacil.utils.permissions.Permissions
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
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



        mapApplication.context = this

        mapViewModel =
            ViewModelProvider(this, MapViewModelFactory(
                MapRepositoryImpl(
                    GoogleMapsService(mapApplication),
                    GoogleAutocompletePlaceService(autocompleteSupportFragment,placesApplication),
                    DirectionsRoutesServices(mapApplication),
                    ManageSettingsLocation(mapApplication),
                    GeofireInFirebase()
                )
            )
            ).get(MapsViewModel::class.java)
        binding.viewmodel = mapViewModel

        // Incializa a variaval de localização do dispositivo(fusedLocationProviderClient)
        mapApplication.fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        mapApplication.locationPermissionGranted = getLocationPermission()
    }

    override fun onResume() {
        mapViewModel.updateLocationDevice()
        Log.w("permission garanted", locationPermissionGranted.toString())
        super.onResume()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mapApplication.mMap=googleMap
        Log.w("permission garanted", locationPermissionGranted.toString())
        inicilizeFramentAutocompletePlaces()
    }

    private fun inicilizeFramentAutocompletePlaces(){
        mapViewModel.inicilizarAutocompletePlaces()
    }

    override fun onStop() {
        mapViewModel.removeLocationDeviceInGeoFireDatabase()
        finish()
        super.onStop()
    }


    fun getLocationPermission(): Boolean {
        Log.w("permission garanted", locationPermissionGranted.toString())
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this.applicationContext,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED) {
            locationPermissionGranted = true
            return locationPermissionGranted
        } else {
            ActivityCompat.requestPermissions(
                this as Activity, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                Permissions.PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
            )
        }

        return false
    }
}