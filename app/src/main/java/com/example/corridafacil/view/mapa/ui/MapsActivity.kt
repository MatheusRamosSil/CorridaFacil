package com.example.corridafacil.view.mapa.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.corridafacil.BuildConfig
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
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.maps.android.compose.*
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException


class MapsActivity : AppCompatActivity(), OnMapReadyCallback, LocationListener
{

    private var locationPermissionGranted = false
    private lateinit var binding: ActivityMapsBinding
    lateinit var mapViewModel: MapsViewModel
    var mapApplication = MapApplication.create()
    lateinit var locationCurrentDevice: LatLng


    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        locationPermissionGranted = getLocationPermission()
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getDeviceLocation()



        //Iniciliza o autocomplete fragment
        val autocompleteSupportFragment =
            (supportFragmentManager.findFragmentById(R.id.autocomplete_fragment)
                    as AutocompleteSupportFragment?) !!

        val placesApplication = PlacesApplication(this)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        /* val mapFragment = supportFragmentManager
             .findFragmentById(R.id.map) as SupportMapFragment
         mapFragment.getMapAsync(this)

         */



        mapApplication.context = this

        mapViewModel =
            ViewModelProvider(
                this, MapViewModelFactory(
                    MapRepositoryImpl(
                        GoogleMapsService(mapApplication),
                        GoogleAutocompletePlaceService(
                            autocompleteSupportFragment,
                            placesApplication
                        ),
                        DirectionsRoutesServices(mapApplication),
                        ManageSettingsLocation(mapApplication),
                        GeofireInFirebase(),
                        FirebaseMenssagingServices()
                    )
                )
            ).get(MapsViewModel::class.java)
        binding.viewmodel = mapViewModel

        Places.initialize(this.applicationContext, BuildConfig.GOOGLE_MAPS_API_KEY)
        mapViewModel.placesClient = Places.createClient(this)

        // Incializa a variaval de localização do dispositivo(fusedLocationProviderClient)
        mapApplication.fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(this)
        mapApplication.locationPermissionGranted = getLocationPermission()


        initializeRequestLocationUpdates();

        Log.w("permission garanted", locationPermissionGranted.toString())


    }

    override fun onResume()
    {
        super.onResume()
        setContent {


            getDeviceLocation()

            val cameraPositionState = rememberCameraPositionState()

            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState,
                properties = MapProperties(isMyLocationEnabled = true)
            ) {



            }


        }

    }


    @SuppressLint("MissingPermission")
    private fun initializeRequestLocationUpdates()
    {
        val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1.0f, this)
    }

    override fun onStart()
    {

        Log.w("permission garanted", locationPermissionGranted.toString())
        super.onStart()
    }

    override fun onMapReady(googleMap: GoogleMap)
    {
        mapApplication.mMap = googleMap
        mapViewModel.getLastLocationDevice()
        inicilizeFramentAutocompletePlaces()
    }

    private fun inicilizeFramentAutocompletePlaces()
    {
        mapViewModel.inicilizarAutocompletePlaces()
    }

    override fun onStop()
    {
        mapViewModel.removeLocationDeviceInGeoFireDatabase()
        finish()
        super.onStop()
    }


    fun getLocationPermission(): Boolean
    {
        Log.w("permission garanted", locationPermissionGranted.toString())
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(
                this.applicationContext,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            )
            == PackageManager.PERMISSION_GRANTED
        )
        {
            locationPermissionGranted = true
            getDeviceLocation()
            return locationPermissionGranted
        } else
        {
            ActivityCompat.requestPermissions(
                this as Activity, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                Permissions.PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
            )
        }

        return false
    }

    @SuppressLint("MissingPermission")
    fun getDeviceLocation()
    {
        val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        try
        {
            if (locationPermissionGranted == true)
            {
                val locationResult = fusedLocationProviderClient.lastLocation

                locationResult.addOnCompleteListener { task ->
                    if (task.isSuccessful)
                    {
                        val lastKnownLocation = task.result

                        if (lastKnownLocation != null)
                        {
                            locationCurrentDevice = LatLng(
                                lastKnownLocation.latitude,
                                lastKnownLocation.longitude
                            )

                            setContent {
                                val singapore = locationCurrentDevice
                                val cameraPositionState = rememberCameraPositionState {
                                    position = CameraPosition.fromLatLngZoom(singapore, 20f)
                                }

                                Box( modifier = Modifier.fillMaxSize()) {

                                    GoogleMap(
                                        modifier = Modifier.fillMaxSize(),
                                        cameraPositionState = cameraPositionState,
                                        properties = MapProperties(isMyLocationEnabled = true)
                                    ){

                                    }

                                    Surface(
                                        modifier = Modifier
                                            .align(Alignment.BottomCenter)
                                            .padding(8.dp)
                                            .fillMaxWidth(),
                                        color = Color.White,
                                        shape = RoundedCornerShape(8.dp)
                                    ) {
                                        Column(
                                            modifier = Modifier
                                                .padding(16.dp),
                                            horizontalAlignment = Alignment.CenterHorizontally
                                        ) {
                                            var text by remember { mutableStateOf("") }

                                            AnimatedVisibility(
                                                mapViewModel.locationAutofill.isNotEmpty(),
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(8.dp)
                                            ) {
                                                LazyColumn(
                                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                                ) {
                                                    items(mapViewModel.locationAutofill) {
                                                        Row(
                                                            modifier = Modifier
                                                                .fillMaxWidth()
                                                                .padding(16.dp)
                                                                .clickable {
                                                                    text = it.address
                                                                    mapViewModel.locationAutofill.clear()
                                                                    mapViewModel.getCoordinates(it)
                                                                }

                                                        ) {
                                                            Text(it.address)
                                                        }
                                                    }
                                                }
                                                Spacer(Modifier.height(16.dp))
                                            }
                                            OutlinedTextField(
                                                value = text,
                                                onValueChange = {
                                                    text = it
                                                    mapViewModel.searchPlaces(it)
                                                },
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(8.dp)
                                            )
                                        }
                                    }
                                }

                            }

                            Log.w("Current Location", "${locationCurrentDevice}")
                            mapViewModel.minhaLocalizacao = LatLng(
                                lastKnownLocation.latitude,
                                lastKnownLocation.longitude
                            )

                        }
                    } else
                    {
                        Log.d("Exception", " Current User location is null")
                    }
                }

            }

        } catch (e: SecurityException)
        {
            Log.d("Exception", "Exception:  $e.message.toString()")
        }

    }

    override fun onLocationChanged(p0: Location)
    {
        mapViewModel.getUpdateLocationDevice(p0)
    }

}