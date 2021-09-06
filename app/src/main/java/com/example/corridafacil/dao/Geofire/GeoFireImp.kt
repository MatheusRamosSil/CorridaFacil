package com.example.corridafacil.dao.Geofire

import com.firebase.geofire.GeoLocation

interface GeoFireImp {

    fun succesOnLocationResul(key: String, location: GeoLocation?)
    fun onFailure(toString: String)
    fun getKeyExited(keyExited: String)

}

