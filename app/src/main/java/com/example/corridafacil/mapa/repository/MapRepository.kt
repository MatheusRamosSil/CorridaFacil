package com.example.corridafacil.mapa.repository

import com.example.corridafacil.Services.GoogleAutocompletePlacesService.GoogleAutocompletePlaceService
import com.example.corridafacil.Services.GoogleAutocompletePlacesService.GoogleAutocompletePlaceServiceImp
import com.example.corridafacil.Services.GoogleMapsService.GoogleMapsService
import com.example.corridafacil.Services.GoogleMapsService.GoogleMapsSeviceImp
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds

class MapRepository(private var googleMapsService: GoogleMapsService,
                    private var googleAutocompletePlaceService: GoogleAutocompletePlaceService) {

    fun getLocationDevice( googleMapsSeviceImp: GoogleMapsSeviceImp){
       googleMapsService.getDeviceLocation(googleMapsSeviceImp)
    }

    fun inicilizarAutocompletePlace( googleAutocompletePlaceServiceImp: GoogleAutocompletePlaceServiceImp){
        googleAutocompletePlaceService.inicilizarAutocompletePlaces(googleAutocompletePlaceServiceImp)
    }

    fun marcarPontos(multiplePoints: ArrayList<LatLng>) =
            googleMapsService.marcarVariosPontosNoMapa(multiplePoints)

    fun addPointInMap(newPoint:LatLng) = googleMapsService.adicionarNovoPontoNoMapa(newPoint)

    fun moverVisualizacao(tamanhoDaVisualicao:LatLngBounds){
        googleMapsService.moverVisualizacaoParaALocazicaoDoDispositivo(tamanhoDaVisualicao)
    }

    fun marcarRotasNoMapa(origin:LatLng,destination:LatLng){
        googleMapsService.criarRotas(origin,destination)
    }

    fun limintandoBuscaARegiaoDoDispositivo(locationDevice:LatLng){
        googleAutocompletePlaceService.restrigindoResultadosDeAutocompleteParaALocazicaoDoDispositivo(locationDevice)
    }


}
