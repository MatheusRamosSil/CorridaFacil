package com.example.corridafacil.mapa.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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
              testGeofireImplements()
              carregandoDispositivosProximos(myDeviceLocation,20.0)

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

    }

    fun testGeofireImplements(){
        var geofireInFirebase = GeofireInFirebase()
        geofireInFirebase.saveDataLocationInFirebaseDataBase("device4", GeoLocation(-7.6347158,-37.8795776))
        geofireInFirebase.saveDataLocationInFirebaseDataBase("device3", GeoLocation(-7.6346813,-37.8806587))
        geofireInFirebase.saveDataLocationInFirebaseDataBase("device2", GeoLocation(-7.6347984,-37.8796987))
        geofireInFirebase.saveDataLocationInFirebaseDataBase("device1", GeoLocation(-7.634065, -37.879855))

    }

    fun carregandoDispositivosProximos(myLoacationDevices:LatLng,raioDeBusca:Double){

        val geofireInFirebase = GeofireInFirebase()
        val markersDriversHashMap = HashMap<String,Marker>()

        geofireInFirebase.buscandoDispositivosProximos(GeoLocation(myLoacationDevices.latitude
                                                       ,myLoacationDevices.longitude)
                                                       ,raioDeBusca,object :GeoFireImp{
                override fun succesOnLocationResul(key: String, location: GeoLocation?) {

                     val marker = mapRepository.addPointInMap(LatLng(location!!.latitude,location.longitude))
                    markersDriversHashMap.put(key,marker)
                }

                override fun getKeyExited(keyExited: String) {
                    mapRepository.removeMarkerInMarkerList(keyExited,markersDriversHashMap)
                }

                override fun onFailure(toString: String) {
                    Log.i("Error", toString)
                }
            })
    }


}

