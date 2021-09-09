package com.example.corridafacil.mapa.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.corridafacil.Services.APIWeb.Retrofit.RetrofitClient
import com.example.corridafacil.dao.Geofire.GeoFireImp
import com.example.corridafacil.dao.Geofire.GeofireInFirebase
import com.example.corridafacil.Services.GoogleAutocompletePlacesService.GoogleAutocompletePlaceServiceImp
import com.example.corridafacil.Services.GoogleMapsService.GoogleMapsSeviceImp
import com.example.corridafacil.mapa.repository.MapRepository
import com.firebase.geofire.GeoLocation
import com.google.android.gms.common.api.Status
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Marker
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
              carregandoDispositivosProximos(myDeviceLocation,20.0)
              testCriarRotas(myDeviceLocation)

          }

          override fun onFailure(menssage: String) {
              errorMapsStatusValue.postValue(menssage)
          }
      })
    }

    private fun testCriarRotas(myDeviceLocation: LatLng) {
        val meuDispositivo = myDeviceLocation.latitude.toString()+","+myDeviceLocation.longitude.toString()
        val destinaiton = LatLng(-7.7480174,-37.6346628)
        val outroLocal = destinaiton.latitude.toString()+","+destinaiton.longitude.toString()
        Log.i("My device", meuDispositivo)
        Log.i("My Destino", outroLocal)
        mapRepository.addPolines(meuDispositivo,outroLocal)
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

