package com.example.corridafacil.mapa.repository

import com.example.corridafacil.Services.GoogleAutocompletePlacesService.GoogleAutocompletePlaceService
import com.example.corridafacil.Services.GoogleAutocompletePlacesService.GoogleAutocompletePlaceServiceImp
import com.example.corridafacil.Services.GoogleMapsService.GoogleMapsService
import com.example.corridafacil.Services.GoogleMapsService.GoogleMapsSeviceImp
import com.example.corridafacil.dao.Geofire.GeofireInFirebase
import com.firebase.geofire.GeoLocation
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Marker

class MapRepository(private var googleMapsService: GoogleMapsService,
                    private var googleAutocompletePlaceService: GoogleAutocompletePlaceService) {

    var geofireInFirebase = GeofireInFirebase()

    fun getLocationDevice( googleMapsSeviceImp: GoogleMapsSeviceImp){
       googleMapsService.getDeviceLocation(googleMapsSeviceImp)
    }


    fun inicilizarAutocompletePlace( googleAutocompletePlaceServiceImp: GoogleAutocompletePlaceServiceImp){
        googleAutocompletePlaceService.inicilizarAutocompletePlaces(googleAutocompletePlaceServiceImp)
    }


    fun addPointInMap(newPoint:LatLng) = googleMapsService.adicionarNovoPontoNoMapa(newPoint)


    fun moverVisualizacao(tamanhoDaVisualicao:LatLngBounds){
        googleMapsService.moverVisualizacaoParaALocazicaoDoDispositivo(tamanhoDaVisualicao)
    }


    fun clearMap() {
        googleMapsService.cleaningMap()
    }


    fun saveDataInDatabaseGeoFire(key:String,location: GeoLocation){
        geofireInFirebase.saveDataLocationInFirebaseDataBase(key,location)
    }

    fun removeMarkerInMarkerList(key: String, hashMapMarker: HashMap<String,Marker>){
        googleMapsService.removerMarcadorEmUmaLista(key, hashMapMarker)
    }

    fun removeMarker(marker: Marker) = googleMapsService.removerMarcardorDoMapa(marker)

}
