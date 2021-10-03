package com.example.corridafacil.mapa.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.corridafacil.Services.DirectionsRoutes.Retrofit.DirectionsRoutesImp
import com.example.corridafacil.Services.DirectionsRoutes.Retrofit.Models.DirectionResponses
import com.example.corridafacil.Services.DirectionsRoutes.Retrofit.Models.InputDataRoutes
import com.example.corridafacil.dao.Geofire.GeoFireImp
import com.example.corridafacil.Services.GoogleAutocompletePlacesService.GoogleAutocompletePlaceServiceImp
import com.example.corridafacil.Services.GoogleMapsService.GoogleMapsSeviceImp
import com.example.corridafacil.mapa.Utils.ContantsMaps
import com.example.corridafacil.mapa.Utils.Others.ConvertData
import com.example.corridafacil.mapa.repository.MapRepository
import com.firebase.geofire.GeoLocation
import com.google.android.gms.common.api.Status
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Marker
import com.google.android.libraries.places.api.model.Place
import retrofit2.Response

class MapsViewModel(private val mapRepository: MapRepository) : ViewModel(){

    var mapStatusValue = MutableLiveData<String>()
    var errorMapsStatusValue = MutableLiveData<String>()
    var limitesDaVisualizacao = LatLngBounds.builder()
    lateinit var minhaLocalizacao : LatLng


    fun getDeviceLocation() {
      mapRepository.getLocationDevice(object : GoogleMapsSeviceImp {
          override fun onSucess(myDeviceLocation: LatLng) {
              limitesDaVisualizacao.include(myDeviceLocation)
              minhaLocalizacao = myDeviceLocation
              moverVisualizacaoDoMapa()
              carregandoDispositivosProximos(myDeviceLocation,ContantsMaps.RAIO_DE_BUSCA)

          }

          override fun onFailure(menssage: String) {
              errorMapsStatusValue.postValue(menssage)
          }
      })
    }

    private fun inicializarRotas(pontoPartida: LatLng, pontoChegada: LatLng?) {

        val inputDataRoutes = InputDataRoutes.create()
        inputDataRoutes.origin = ConvertData().latLngToString(pontoPartida)
        inputDataRoutes.destination = ConvertData().latLngToString(pontoChegada)
        inputDataRoutes.modeDriving = "driving"
        inputDataRoutes.rotasAlternativas = true

            mapRepository.initRoutes(inputDataRoutes,object : DirectionsRoutesImp{
                    override fun onSuccess(response: Response<DirectionResponses>) {
                        //mapRepository.getMultiplesRoutes(response)
                        mapRepository.getRoute(response)
                    }

                    override fun onFailure(localizedMessage: String?) {
                        Log.i("Error routes", localizedMessage.toString())
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
                mapRepository.clearMap()
                limitesDaVisualizacao.include(place.latLng)
                moverVisualizacaoDoMapa()
                place.latLng?.let { mapRepository.addPointInMap(it)
                                     inicializarRotas(minhaLocalizacao,it)
                }
            }

            override fun onError(status: Status) {

            }
        })

    }


    fun carregandoDispositivosProximos(myLoacationDevices:LatLng,raioDeBusca:Double){

        val markersDriversHashMap = HashMap<String,Marker>()

        mapRepository.loadingNearbyDriversDevices(myLoacationDevices,raioDeBusca, object : GeoFireImp{
            override fun succesOnLocationResul(key: String, location: GeoLocation?) {
                val marker = mapRepository.addPointInMap(LatLng(location!!.latitude,location.longitude))
                markersDriversHashMap.put(key,marker)
            }

            override fun onFailure(toString: String) {
                Log.i("Error", toString)
            }

            override fun getKeyExited(keyExited: String) {
                mapRepository.removeMarkerInMarkerList(keyExited,markersDriversHashMap)
            }
        })
    }


}



