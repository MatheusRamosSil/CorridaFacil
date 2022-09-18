package com.example.corridafacil.view.mapa.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.corridafacil.R
import com.example.corridafacil.domain.services.DirectionsRoutes.Retrofit.DirectionsRoutesServices
import com.example.corridafacil.domain.services.FirebaseMenssaging.FirebaseMenssagingServices
import com.example.corridafacil.domain.services.GoogleAutocompletePlacesService.GoogleAutocompletePlaceService
import com.example.corridafacil.domain.services.GoogleAutocompletePlacesService.Models.PlacesApplication
import com.example.corridafacil.domain.services.GoogleMapsService.GoogleMapsService
import com.example.corridafacil.domain.services.GoogleMapsService.Models.MapApplication
import com.example.corridafacil.databinding.ActivityMapsBinding
import com.example.corridafacil.utils.mapa.Others.ManageSettingsLocation
import com.example.corridafacil.data.repository.mapa.MapRepositoryImpl

import com.example.corridafacil.data.models.Geofire.GeofireInFirebase
import com.example.corridafacil.utils.permissions.Permissions
import com.example.corridafacil.view.mapa.viewModel.MapsViewModel
import com.example.corridafacil.view.mapa.viewModel.factories.MapViewModelFactory
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.libraries.places.widget.AutocompleteSupportFragment


class MapsActivity : AppCompatActivity(), OnMapReadyCallback, LocationListener{

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
                    GeofireInFirebase(),
                    FirebaseMenssagingServices()
                )
            )
            ).get(MapsViewModel::class.java)
        binding.viewmodel = mapViewModel

        // Incializa a variaval de localização do dispositivo(fusedLocationProviderClient)
        mapApplication.fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        mapApplication.locationPermissionGranted = getLocationPermission()


        initializeRequestLocationUpdates();

        Log.w("permission garanted", locationPermissionGranted.toString())


    }

    @SuppressLint("MissingPermission")
    private fun initializeRequestLocationUpdates() {
        val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000,1.0f,this)
    }

    override fun onStart() {

        Log.w("permission garanted", locationPermissionGranted.toString())
        super.onStart()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mapApplication.mMap=googleMap
        mapViewModel.getLastLocationDevice()
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

    override fun onLocationChanged(p0: Location) {
        mapViewModel.getUpdateLocationDevice(p0)
    }

}