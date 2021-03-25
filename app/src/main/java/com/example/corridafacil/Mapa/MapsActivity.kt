package com.example.corridafacil.Mapa

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.media.audiofx.BassBoost
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.corridafacil.R
import com.google.android.gms.location.*

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import java.util.jar.Manifest

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var mMap: GoogleMap
    private var map: GoogleMap? = null
    private var locationPermissionGranted = false
    private var lastKnownLocation: Location? = null
    private val defaultLocation = LatLng(-33.8523341, 151.2106085)
    override fun onDestroy() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
        super.onDestroy()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        startVariable()
        checkGPSEnabled()
    }

    private fun startVariable() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

    }
    private fun checkGPSEnabled(){
        if (!isLocationEnabled()){
            showAlert()
        }

    }

    private fun showAlert() {
        val dialog = AlertDialog.Builder(this)
        dialog.setTitle("Ative sua localização")
                .setMessage("A localização do seu dispositivo deve estar 'desligada'.\nPor favor ative sua localização")
                .setPositiveButton("Abrir configurações") { paramDialogInterface, paramInt ->
                    val myIntent = Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                    startActivity(myIntent)
                }
                .setNegativeButton("Cancel") { paramDialogInterface, paramInt -> }
        dialog.show()
    }

    private fun isLocationEnabled(): Boolean {
        var locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager!!.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        getLocationPermission()
        getDeviceLocation()
    }



    private fun getDeviceLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            if (locationPermissionGranted) {
                val locationResult = fusedLocationProviderClient.lastLocation
                locationResult.addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Set the map's camera position to the current location of the device.
                        lastKnownLocation = task.result
                        if (lastKnownLocation != null) {
                            val myDeviceLocation = LatLng(lastKnownLocation!!.latitude,lastKnownLocation!!.longitude)
                            mMap.isMyLocationEnabled = true
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myDeviceLocation, 18f))
                        }
                    } else {
                        map?.moveCamera(CameraUpdateFactory
                            .newLatLngZoom(defaultLocation, 15f))
                        map?.uiSettings?.isMyLocationButtonEnabled = false
                    }
                }
            }
        } catch (e: SecurityException) {

        }
    }
    private fun getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this.applicationContext,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED) {
            locationPermissionGranted = true
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                Companion.PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
            )
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

    companion object {
        private const val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1
    }

}


