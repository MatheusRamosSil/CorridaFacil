package com.example.corridafacil.utils.permissions

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

object Permissions {
    const val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1
    const val PERMISSION_ACEESS_FINE_LOCATION = 101
    const val PERMISSION_READ_EXTERNAL_STORAGE = 102

    private var locationPermissionGranted = false

    fun Context.hasReadExternalStoragePermission() =
        ActivityCompat.checkSelfPermission(this,
            Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED

    fun Context.isLocationEnabled(): Boolean {
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER)
    }

    fun Context.acessFineLocationPermissions() =  ContextCompat
                                                    .checkSelfPermission(applicationContext,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED



    fun Context.checkForPermissions(activity: Activity, permission: String, name :String, resquestCode: Int){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            return when{
                acessFineLocationPermissions() -> {
                    Toast.makeText(applicationContext, "$name permission granted", Toast.LENGTH_SHORT).show()
                }
                hasReadExternalStoragePermission() ->{
                    Toast.makeText(applicationContext, "$name permission granted", Toast.LENGTH_SHORT).show()
                }

                ActivityCompat.shouldShowRequestPermissionRationale(this as Activity, permission) -> showDialog(permission,name,resquestCode,activity)

                else -> ActivityCompat.requestPermissions(this , arrayOf(permission),resquestCode)
            }
        }
    }

    fun Context.showDialog(permission: String, name: String, resquestCode: Int, activity: Activity) {
        val builder = AlertDialog.Builder(this)
        builder.apply {
            setMessage("Permission to acess your $name is required to use this app")
            setTitle("Permission required")
            setPositiveButton("Ok"){ dialog,which ->
                ActivityCompat.requestPermissions(activity, arrayOf(permission),resquestCode)
            }
        }
        val dialog : AlertDialog = builder.create()
        dialog.show()
    }


}