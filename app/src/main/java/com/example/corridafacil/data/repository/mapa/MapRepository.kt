package com.example.corridafacil.data.repository.mapa

import android.location.Location
import com.example.corridafacil.domain.services.DirectionsRoutes.Retrofit.Models.DirectionResponses
import com.example.corridafacil.domain.services.DirectionsRoutes.Retrofit.Models.InputDataRoutes
import com.example.corridafacil.domain.services.GoogleAutocompletePlacesService.GoogleAutocompletePlaceServiceImp
import com.example.corridafacil.data.models.Geofire.GeoFireImp

import com.firebase.geofire.GeoLocation
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Marker
import retrofit2.Response

interface MapRepository {

    suspend fun getLocationDevice(): LatLng


    fun inicilizarAutocompletePlace( googleAutocompletePlaceServiceImp: GoogleAutocompletePlaceServiceImp)


    fun addMakerInLocationDevice(deviceLocation:LatLng)


    //fun addPointInMap(newPoint:LatLng): Marker


    fun moverVisualizacao(tamanhoDaVisualicao: LatLngBounds)


    fun getMultiplesRoutes(response: Response<DirectionResponses>)


    fun getRoute(response: DirectionResponses?)


    fun clearMap()


    fun createLocationRequest(): LocationRequest


    fun startLocationUpdates(locationRequest: LocationRequest, locationCallback: LocationCallback)


    fun stopLocationUpdates(locationCallback: LocationCallback)


    fun saveDataInDatabaseGeoFire(key:String?,location: GeoLocation)


    fun updateDataLocationDeviceInDataBase(uidAuth:String, dataUpdated: Map<String,GeoLocation>)


    fun removeMarkerInMarkerList(key: String, hashMapMarker: HashMap<String?, Marker>)


    fun removeLocationDeviceInGeofire(key: String?)


    fun removeMarker(marker: Marker?)


    suspend fun initRoutes(inputDataRoutes: InputDataRoutes): DirectionResponses?


    fun loadingNearbyDriversDevices(myLocationDevice: LatLng, raioDeBusca: Double, geofireImp: GeoFireImp)


   // suspend fun sendNotification(tokenDriver: String)

    suspend fun onLocationChanged():Location

}
