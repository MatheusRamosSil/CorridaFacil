package com.example.corridafacil.mapa.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.corridafacil.Services.GoogleAutocompletePlacesService.GoogleAutocompletePlaceServiceImp
import com.example.corridafacil.Services.GoogleMapsService.GoogleMapsSeviceImp
import com.example.corridafacil.mapa.repository.MapRepository
import com.google.android.gms.common.api.Status
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place

class MapsViewModel(private val mapRepository: MapRepository) : ViewModel(){

    var mapStatusValue = MutableLiveData<Boolean>()
    var errorMapsStatusValue = MutableLiveData<String>()
    var limitesDaVisualizacao = LatLngBounds.builder()
    lateinit var tamanhoDaVisualizacao:LatLngBounds
    lateinit var minhaLocalizacao:LatLng


    fun getDeviceLocation() {
      mapRepository.getLocationDevice(object : GoogleMapsSeviceImp {
          override fun onSucess(myDeviceLocation: LatLng) {
              mapStatusValue.postValue(true)
              limitesDaVisualizacao.include(myDeviceLocation)
              minhaLocalizacao = myDeviceLocation
              moverVisualizacaoDoMapa()
          }

          override fun onFailure(menssage: String) {
              errorMapsStatusValue.postValue(menssage)
          }
      })
    }

    fun moverVisualizacaoDoMapa() {
        tamanhoDaVisualizacao = limitesDaVisualizacao.build()
        mapRepository.moverVisualizacao(tamanhoDaVisualizacao)
    }

    fun inicilizarAutocompletePlaces(){
        mapRepository.inicilizarAutocompletePlace(object : GoogleAutocompletePlaceServiceImp{
            override fun onSuccess(place: Place) {
                place.latLng?.let { mapRepository.addPointInMap(it) }
                limitesDaVisualizacao.include(place.latLng)
                moverVisualizacaoDoMapa()
                mapRepository.limintandoBuscaARegiaoDoDispositivo(minhaLocalizacao)
            }

            override fun onError(status: Status) {

            }
        })


    }

    fun testMarkersTwoPoints(){
        tamanhoDaVisualizacao = limitesDaVisualizacao.build()
        mapRepository.moverVisualizacao(tamanhoDaVisualizacao)
    }

}
