package com.example.corridafacil.mapa.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.corridafacil.Services.DirectionsRoutes.Retrofit.Models.InputDataRoutes
import com.example.corridafacil.models.Geofire.GeoFireImp
import com.example.corridafacil.Services.GoogleAutocompletePlacesService.GoogleAutocompletePlaceServiceImp
import com.example.corridafacil.mapa.Utils.ContantsMaps
import com.example.corridafacil.mapa.Utils.Others.ConvertData
import com.example.corridafacil.mapa.repository.MapRepository
import com.firebase.geofire.GeoLocation
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Marker
import com.google.android.libraries.places.api.model.Place
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import java.lang.Exception

class MapsViewModel(private val mapRepository: MapRepository) : ViewModel(){

    var mapStatusValue = MutableLiveData<String>()
    var errorMapsStatusValue = MutableLiveData<String>()
    var limitesDaVisualizacao = LatLngBounds.builder()
    lateinit var minhaLocalizacao : LatLng
    private val resultLocationRequest: LocationRequest

   init{
       resultLocationRequest = mapRepository.createLocationRequest()
    }

    private val locationCallback = object : LocationCallback(){
        override fun onLocationResult(p0: LocationResult) {
            minhaLocalizacao = LatLng(p0.lastLocation.latitude,p0.lastLocation.longitude)
            mapRepository.addMakerInLocationDevice(minhaLocalizacao)
            saveDataLocationsInGeoFire(minhaLocalizacao)
            carregandoDispositivosProximos(minhaLocalizacao,ContantsMaps.RAIO_DE_BUSCA)
        }
    }

    fun updateLocationDevice(){
        mapRepository.startLocationUpdates(resultLocationRequest,locationCallback)
    }

    fun saveDataLocationsInGeoFire(deviceLocation: LatLng){
        val locationDeviceInGeoFire = GeoLocation(deviceLocation.latitude,deviceLocation.longitude)
        val userUID = FirebaseAuth.getInstance().currentUser?.uid
        mapRepository.saveDataInDatabaseGeoFire(userUID,locationDeviceInGeoFire)
    }

    fun removeLocationDeviceInGeoFireDatabase(){
        val userUID = FirebaseAuth.getInstance().currentUser?.uid
        mapRepository.removeLocationDeviceInGeofire(userUID)
    }

    private fun inicializarRotas(pontoPartida: LatLng, pontoChegada: LatLng?) {

        val inputDataRoutes = InputDataRoutes.create()
        inputDataRoutes.origin = ConvertData().latLngToString(pontoPartida)
        inputDataRoutes.destination = ConvertData().latLngToString(pontoChegada)
        inputDataRoutes.modeDriving = "driving"
        inputDataRoutes.rotasAlternativas = true

        viewModelScope.launch {
            try {
                val resultRoutes = mapRepository.initRoutes(inputDataRoutes)

                mapRepository.getRoute(resultRoutes)

                val viewSizeNortheast = LatLng(resultRoutes?.routes?.get(0)?.bounds?.northeast?.lat!!,
                                               resultRoutes.routes?.get(0)?.bounds?.northeast?.lng!!)

                val viewSizeSouthwest = LatLng(resultRoutes.routes?.get(0)?.bounds?.southwest?.lat!!,
                                               resultRoutes.routes?.get(0)?.bounds?.southwest?.lng!!)

                moverVisualizacaoDoMapa(viewSizeSouthwest,viewSizeNortheast)
            }catch (exception : Exception){
                Log.w("Error load routes", exception.message.toString())
            }
        }
    }


    fun moverVisualizacaoDoMapa( viewSizeSouthwest: LatLng, viewSizeNortheast: LatLng) {
        val tamanhoDaVisualizacao = LatLngBounds(viewSizeSouthwest,viewSizeNortheast)
        mapRepository.moverVisualizacao(tamanhoDaVisualizacao)
    }

    fun inicilizarAutocompletePlaces() {
        mapRepository.inicilizarAutocompletePlace(object : GoogleAutocompletePlaceServiceImp{
            override fun onSuccess(place: Place) {
                mapRepository.clearMap()
                limitesDaVisualizacao.include(place.latLng)
                place.latLng?.let { mapRepository.addPointInMap(it)
                                     inicializarRotas(minhaLocalizacao,it)
                }
            }

            override fun getAdress(adress: String) {
                Log.w("Places adress", adress)
            }
            override fun onError(status: Exception) {

            }
        })

    }


    fun carregandoDispositivosProximos(myLoacationDevices:LatLng,raioDeBusca:Double){

        val markersDriversHashMap = HashMap<String?,Marker>()

        mapRepository.loadingNearbyDriversDevices(myLoacationDevices,raioDeBusca, object : GeoFireImp{
            override fun succesOnLocationResul(key: String, location: GeoLocation?) {
                val marker = mapRepository.addPointInMap(LatLng(location!!.latitude,location.longitude))
                markersDriversHashMap.put(key,marker)
            }


            override fun getKeyExited(keyExited: String) {
                mapRepository.removeMarkerInMarkerList(keyExited,markersDriversHashMap)
                mapRepository.removeMarker(markersDriversHashMap[keyExited])
            }

            override fun getKeyMoved(key: String?, location: GeoLocation) {
                mapRepository.removeMarker(markersDriversHashMap[key])
                val marker = mapRepository.addPointInMap(LatLng(location.latitude,location.longitude))
                markersDriversHashMap.put(key,marker)
            }

            override fun onFailure(toString: String) {
                Log.i("Error", toString)
            }

        })
    }


}



