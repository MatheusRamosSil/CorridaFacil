package com.example.corridafacil.mapa.repository

import com.example.corridafacil.Services.DirectionsRoutes.Retrofit.DirectionsRoutesImp
import com.example.corridafacil.Services.DirectionsRoutes.Retrofit.DirectionsRoutesServices
import com.example.corridafacil.Services.DirectionsRoutes.Retrofit.Models.DirectionResponses
import com.example.corridafacil.Services.DirectionsRoutes.Retrofit.Models.InputDataRoutes
import com.example.corridafacil.Services.GoogleAutocompletePlacesService.GoogleAutocompletePlaceService
import com.example.corridafacil.Services.GoogleAutocompletePlacesService.GoogleAutocompletePlaceServiceImp
import com.example.corridafacil.Services.GoogleMapsService.GoogleMapsService
import com.example.corridafacil.Services.GoogleMapsService.GoogleMapsSeviceImp
import com.example.corridafacil.models.Geofire.GeoFireImp
import com.example.corridafacil.models.Geofire.GeofireInFirebase
import com.example.corridafacil.mapa.Utils.Others.ManageSettingsLocation
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
    private var geofireInFirebase: GeofireInFirebase
) : MapRepository {



    override fun getLocationDevice(googleMapsSeviceImp: GoogleMapsSeviceImp){
       googleMapsService.getDeviceLocation(googleMapsSeviceImp)
    }

    override fun loadingNearbyDriversDevices(myLocationDevice : LatLng, raioDeBusca: Double, geofireImp:GeoFireImp){
        geofireInFirebase.buscandoDispositivosProximos(myLocationDevice,raioDeBusca, geofireImp)
    }

    override fun saveDataInDatabaseGeoFire(key:String?, location: GeoLocation){
        geofireInFirebase.saveDataLocationInFirebaseDataBase(key,location)
    }

    override fun removeLocationDeviceInGeofire(key: String?){
        geofireInFirebase.removeLocationDevice(key)
    }

    override fun inicilizarAutocompletePlace(googleAutocompletePlaceServiceImp: GoogleAutocompletePlaceServiceImp){
        googleAutocompletePlaceService.inicilizarAutocompletePlaces(googleAutocompletePlaceServiceImp)
    }

    override fun addMakerInLocationDevice(deviceLocation:LatLng) = googleMapsService.addMarkerInLocationDevice(deviceLocation)

    override fun addPointInMap(newPoint:LatLng): Marker {
        return googleMapsService.adicionarNovoPontoNoMapa(newPoint)
    }


    override fun moverVisualizacao(tamanhoDaVisualicao:LatLngBounds){
        googleMapsService.moverVisualizacaoParaALocazicaoDoDispositivo(tamanhoDaVisualicao)
    }

    override fun initRoutes(inputDataRoutes: InputDataRoutes, directionsRoutesImp:DirectionsRoutesImp){
        directionsRoutesServices.createRoutes(inputDataRoutes,directionsRoutesImp)
    }

    override fun getMultiplesRoutes(response: Response<DirectionResponses>){
        directionsRoutesServices.showMultiplesRoutes(response)
    }

    override fun getRoute(response: Response<DirectionResponses>){
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


