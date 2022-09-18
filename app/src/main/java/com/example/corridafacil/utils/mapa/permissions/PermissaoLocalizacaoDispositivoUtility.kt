package com.example.corridafacil.utils.mapa.permissions

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.corridafacil.utils.mapa.permissions.Constantes.PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
import pub.devrel.easypermissions.EasyPermissions


/*
   * Request location permission, so that we can get the location of the
   * device. The result of the permission request is handled by a callback,
   * onRequestPermissionsResult.
   */
    fun Context.getLocationPermission(): Boolean {
        if(ContextCompat.checkSelfPermission(this.applicationContext,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED){
            return true
        }else{
            ActivityCompat.requestPermissions(
                this as Activity, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
            PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION)
        }
        return false
    }

    fun Context.hasLocationPermissions() =
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            EasyPermissions.hasPermissions(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        } else {
            EasyPermissions.hasPermissions(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION
            )
        }







