package com.example.corridafacil.dao.Geofire

import android.util.Log
import com.example.corridafacil.dao.PassageiroDAO
import com.firebase.geofire.*
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase


class GeofireInFirebase() : PassageiroDAO(){
    var referenceDataBaseFirebase = FirebaseDatabase.getInstance().getReference("Location")
    var geoFire = GeoFire(referenceDataBaseFirebase)

    fun saveDataLocationInFirebaseDataBase(key:String,location: GeoLocation){
        geoFire.setLocation(key, location, object : GeoFire.CompletionListener{
            override fun onComplete(key: String?, error: DatabaseError?) {
                if (error != null){
                    Log.i("OK","")
                } else{
                    Log.i("Error","")
                }
            }
        })
    }


    fun buscandoDispositivosProximos(myLocation:LatLng,raioDeBusca: Double, geofireImp: GeoFireImp){
        val geoQuery =  geoFire.queryAtLocation(GeoLocation(myLocation.latitude,myLocation.longitude),raioDeBusca)
        val devicesLocations = ArrayList<GeoLocation>()
        geoQuery.addGeoQueryEventListener( object : GeoQueryEventListener{
            override fun onKeyEntered(key: String?, location: GeoLocation?) {
                devicesLocations.add(location!!)
                geofireImp.succesOnLocationResul(key!!,location)
            }

            override fun onKeyExited(key: String?) {
                Log.i("Exited verify",key.toString())
                geofireImp.getKeyExited(key!!)
            }

            override fun onKeyMoved(key: String?, location: GeoLocation?) {
                Log.i("Moved verify", key+" "+location!!.latitude.toString()+" "+location.longitude.toString())
            }

            override fun onGeoQueryReady() {
                Log.i("Read verify ","All initial data has been loaded and events have been fired!")
            }

            override fun onGeoQueryError(error: DatabaseError?) {
                Log.i("Error verify","There was an error with this query: " + error)
                geofireImp.onFailure(error.toString())

            }
        })
    }


    fun getLocationDevices(key:String){

        geoFire.getLocation(key, object : LocationCallback{
            override fun onLocationResult(key: String?, location: GeoLocation?) {

            }

            override fun onCancelled(databaseError: DatabaseError?) {

            }
        })
    }

}