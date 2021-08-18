package com.example.corridafacil.mapa.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.corridafacil.Services.GoogleAutocompletePlacesService.GoogleAutocompletePlaceServiceImp
import com.example.corridafacil.Services.GoogleMapsService.GoogleMapsSeviceImp
import com.example.corridafacil.mapa.repository.MapRepository
import com.google.android.gms.common.api.Status
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.libraries.places.api.model.Place

class MapsViewModel(private val mapRepository: MapRepository) : ViewModel(){

    var mapStatusValue = MutableLiveData<Boolean>()
    var errorMapsStatusValue = MutableLiveData<String>()
    var limitesDaVisualizacao = LatLngBounds.builder()

    fun getDeviceLocation() {
      mapRepository.getLocationDevice(object : GoogleMapsSeviceImp {
          override fun onSucess(myDeviceLocation: LatLng) {
              mapStatusValue.postValue(true)
              limitesDaVisualizacao.include(myDeviceLocation)
              moverVisualizacaoDoMapa()
          }

          override fun onFailure(menssage: String) {
              errorMapsStatusValue.postValue(menssage)
          }
      })
    }


    fun moverVisualizacaoDoMapa() {
        val tamanhoDaVisualizacao: LatLngBounds = limitesDaVisualizacao.build()
        mapRepository.moverVisualizacao(tamanhoDaVisualizacao)
    }

    fun inicilizarAutocompletePlaces() {
        mapRepository.inicilizarAutocompletePlace(object : GoogleAutocompletePlaceServiceImp{
            override fun onSuccess(place: Place) {
                place.latLng?.let { mapRepository.addPointInMap(it) }
                limitesDaVisualizacao.include(place.latLng)
                moverVisualizacaoDoMapa()
            }

            override fun onError(status: Status) {

            }
        })

        testDrawRoutes()
    }

    fun testDrawRoutes(){
        val p1 = LatLng(-7.6367301,-37.8848102)
        val p2 = LatLng(-7.7508673,-37.6530164)
        mapRepository.marcarRotasNoMapa(p1,p2)
    }


}
