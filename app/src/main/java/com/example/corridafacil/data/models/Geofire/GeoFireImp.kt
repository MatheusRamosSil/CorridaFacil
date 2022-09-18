package com.example.corridafacil.data.models.Geofire

import com.firebase.geofire.GeoLocation

interface GeoFireImp {

    fun succesOnLocationResul(key: String, location: GeoLocation?)
    fun onFailure(toString: String)
    fun getKeyExited(keyExited: String)
    abstract fun getKeyMoved(key: String?, location: GeoLocation)

}

