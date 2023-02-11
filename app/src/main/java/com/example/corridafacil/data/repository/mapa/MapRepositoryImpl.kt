package com.example.corridafacil.data.repository.mapa

import android.location.Location
import com.example.corridafacil.domain.services.DirectionsRoutes.Retrofit.DirectionsRoutesServices
import com.example.corridafacil.domain.services.DirectionsRoutes.Retrofit.Models.DirectionResponses
import com.example.corridafacil.domain.services.DirectionsRoutes.Retrofit.Models.InputDataRoutes
import com.example.corridafacil.domain.services.FirebaseMenssaging.FirebaseMenssagingServices
import com.example.corridafacil.domain.services.GoogleAutocompletePlacesService.GoogleAutocompletePlaceService
import com.example.corridafacil.domain.services.GoogleAutocompletePlacesService.GoogleAutocompletePlaceServiceImp
import com.example.corridafacil.domain.services.GoogleMapsService.GoogleMapsService
import com.example.corridafacil.utils.mapa.Others.ManageSettingsLocation
import com.example.corridafacil.data.models.Geofire.GeoFireImp
import com.example.corridafacil.data.models.Geofire.GeofireInFirebase
import com.firebase.geofire.GeoLocation
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Marker
import retrofit2.Response

class MapRepositoryImpl(
    private var googleMapsService: GoogleMapsService,
    private var googleAutocompletePlaceService: GoogleAutocompletePlaceService,
    private var directionsRoutesServices: DirectionsRoutesServices,
    private var manageSettingsLocation: ManageSettingsLocation,
    private var geofireInFirebase: GeofireInFirebase,
    private var firebaseMenssagingServices: FirebaseMenssagingServices
) : MapRepository {


    /*
    override suspend fun sendNotification(tokenDriver:String){
        firebaseMenssagingServices.sendNotification(tokenDriver)
    }

     */

    override suspend fun onLocationChanged(): Location {
        return googleMapsService.onLocationChanged()
    }

    override suspend fun getLocationDevice(): LatLng {
       return googleMapsService.getDeviceLocation()
    }

    override fun loadingNearbyDriversDevices(myLocationDevice : LatLng, raioDeBusca: Double, geofireImp:GeoFireImp) {
       geofireInFirebase.buscandoDispositivosProximos(myLocationDevice,raioDeBusca, geofireImp)
    }

    override fun saveDataInDatabaseGeoFire(key:String?, location: GeoLocation){
        geofireInFirebase.saveDataLocationInFirebaseDataBase(key,location)
    }

    override fun updateDataLocationDeviceInDataBase(
        uidAuth: String,
        dataUpdated: Map<String, GeoLocation>
    ) {
        geofireInFirebase.updateDataLocationInDataBase(uidAuth, dataUpdated)
    }

    override fun removeLocationDeviceInGeofire(key: String?){
        geofireInFirebase.removeLocationDevice(key)
    }

    override fun inicilizarAutocompletePlace(googleAutocompletePlaceServiceImp: GoogleAutocompletePlaceServiceImp){
        googleAutocompletePlaceService.inicilizarAutocompletePlaces(googleAutocompletePlaceServiceImp)
    }

    override fun addMakerInLocationDevice(deviceLocation:LatLng) = googleMapsService.addMarkerInLocationDevice(deviceLocation)

    /*override fun addPointInMap(newPoint:LatLng): Marker {
        return googleMapsService.adicionarNovoPontoNoMapa(newPoint)
    }

     */


    override fun moverVisualizacao(tamanhoDaVisualicao:LatLngBounds){
        googleMapsService.moverVisualizacaoParaALocazicaoDoDispositivo(tamanhoDaVisualicao)
    }

    override suspend fun initRoutes(inputDataRoutes: InputDataRoutes): DirectionResponses? {
        return directionsRoutesServices.createRoutes(inputDataRoutes)
    }

    override fun getMultiplesRoutes(response: Response<DirectionResponses>){
        directionsRoutesServices.showMultiplesRoutes(response)
    }

    override fun getRoute(response: DirectionResponses?){
        directionsRoutesServices.showRoute(response)
    }

    override fun clearMap() {
        googleMapsService.cleaningMap()
    }

    override fun createLocationRequest(): LocationRequest {
        return manageSettingsLocation.createLocationRequest()
    }

    override fun startLocationUpdates(locationRequest: LocationRequest, locationCallback: LocationCallback){
        manageSettingsLocation.checkManagerLocation(locationRequest, locationCallback)
    }

    override fun stopLocationUpdates(locationCallback: LocationCallback){
        manageSettingsLocation.stopLocationUpdates(locationCallback)
    }

    override fun removeMarkerInMarkerList(key: String, hashMapMarker: HashMap<String?, Marker>){
        googleMapsService.removerMarcadorEmUmaLista(key, hashMapMarker)
    }

    override fun removeMarker(marker: Marker?) = googleMapsService.removerMarcardorDoMapa(marker)

}


