package com.example.corridafacil.mapa.repository

import com.example.corridafacil.Services.DirectionsRoutes.Retrofit.Models.DirectionResponses
import com.example.corridafacil.Services.DirectionsRoutes.Retrofit.Models.InputDataRoutes
import com.example.corridafacil.Services.GoogleAutocompletePlacesService.GoogleAutocompletePlaceServiceImp
import com.example.corridafacil.Services.GoogleMapsService.GoogleMapsSeviceImp
import com.example.corridafacil.models.Geofire.GeoFireImp

import com.firebase.geofire.GeoLocation
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Marker
import okhttp3.ResponseBody
import retrofit2.Response

interface MapRepository {

    fun getLocationDevice( googleMapsSeviceImp: GoogleMapsSeviceImp)


    fun inicilizarAutocompletePlace( googleAutocompletePlaceServiceImp: GoogleAutocompletePlaceServiceImp)


    fun addMakerInLocationDevice(deviceLocation:LatLng)


    fun addPointInMap(newPoint:LatLng): Marker


    fun moverVisualizacao(tamanhoDaVisualicao: LatLngBounds)


    fun getMultiplesRoutes(response: Response<DirectionResponses>)


    fun getRoute(response: DirectionResponses?)


    fun clearMap()


    fun createLocationRequest(): LocationRequest


    fun startLocationUpdates(locationRequest: LocationRequest, locationCallback: LocationCallback)


    fun stopLocationUpdates(locationCallback: LocationCallback)


    fun saveDataInDatabaseGeoFire(key:String?,location: GeoLocation)


    fun removeMarkerInMarkerList(key: String, hashMapMarker: HashMap<String?, Marker>)


    fun removeLocationDeviceInGeofire(key: String?)


    fun removeMarker(marker: Marker?)


    suspend fun initRoutes(inputDataRoutes: InputDataRoutes): DirectionResponses?


    fun loadingNearbyDriversDevices(myLocationDevice: LatLng, raioDeBusca: Double, geofireImp: GeoFireImp)


    suspend fun sendNotification(tokenDriver: String)
}
