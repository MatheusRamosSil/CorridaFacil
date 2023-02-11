package com.example.corridafacil.view.mapa.viewModel

import android.annotation.SuppressLint
import android.location.Location
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.corridafacil.domain.services.DirectionsRoutes.Retrofit.Models.InputDataRoutes
import com.example.corridafacil.domain.services.GoogleAutocompletePlacesService.GoogleAutocompletePlaceServiceImp
import com.example.corridafacil.utils.mapa.ContantsMaps
import com.example.corridafacil.utils.mapa.Others.ConvertData
import com.example.corridafacil.data.repository.mapa.MapRepository
import com.example.corridafacil.data.models.Geofire.GeoFireImp
import com.example.corridafacil.view.auth.viewModel.Result
import com.firebase.geofire.GeoLocation
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Marker
import com.google.android.libraries.places.api.model.Place
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class StateMap{

    data class LocationDevice(val locationDevice: LatLng):StateMap()
    object Empty: StateMap()

}
class MapsViewModel(private val mapRepository: MapRepository) : ViewModel(){

    var limitesDaVisualizacao = LatLngBounds.builder()
    lateinit var minhaLocalizacao : LatLng
    private val resultLocationRequest: LocationRequest

    val _state = MutableStateFlow(LatLng(0.000, 0.0))
    val stateUI : StateFlow<LatLng> = _state

   init{
       resultLocationRequest = mapRepository.createLocationRequest()
    }

    /*
    private val locationCallback = object : LocationCallback(){
        override fun onLocationResult(p0: LocationResult) {
            super.onLocationResult(p0)
            minhaLocalizacao = LatLng(p0.lastLocation.latitude,p0.lastLocation.longitude)
            Log.i("Last Location ", minhaLocalizacao.toString())
            mapRepository.addMakerInLocationDevice(minhaLocalizacao)
            saveDataLocationsInGeoFire(minhaLocalizacao)
            carregandoDispositivosProximos(minhaLocalizacao, ContantsMaps.RAIO_DE_BUSCA)
        }
    }

     */

    fun addMarker(position:LatLng){
        Log.i("Changed locatin", position.toString())
           mapRepository.addMakerInLocationDevice(position)
    }


    fun getLastLocationDevice(){
        viewModelScope.launch {
            minhaLocalizacao = mapRepository.getLocationDevice()
            _state.value = minhaLocalizacao
            Log.i("Device last location", minhaLocalizacao.toString())
            mapRepository.addMakerInLocationDevice(minhaLocalizacao)
           // saveDataLocationsInGeoFire(minhaLocalizacao)
            carregandoDispositivosProximos(minhaLocalizacao, ContantsMaps.RAIO_DE_BUSCA)
        }
    }

    fun saveDataLocationsInGeoFire(deviceLocation: LatLng){
        val locationDeviceInGeoFire = GeoLocation(deviceLocation.latitude,deviceLocation.longitude)
        val userUID = FirebaseAuth.getInstance().currentUser?.uid
       // mapRepository.saveDataInDatabaseGeoFire(userUID,locationDeviceInGeoFire)
    }

    fun updateDataLocationInDataBase(uidAuth: String, dataLocationUpdated : Map<String,GeoLocation>){
        mapRepository.updateDataLocationDeviceInDataBase(uidAuth,dataLocationUpdated)
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

                val token = "e-c_K0ImQe2zN33cvtXXgw:APA91bEcHBjXMggl7rxdeqLdFdWUkYSsnmFuApvWRM-_HxY1IFV6BHOI3Vl54qYpaMfApvdbYmcbcLOf4EXzjRBEaSFsv56h4hm3QW1feNBsNjAouWQPmeO3FNVvBlegKG_nsu0dSfPJ"
                //mapRepository.sendNotification(token)
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
                place.latLng?.let {// mapRepository.addPointInMap(it)
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
               // val marker = mapRepository.addPointInMap(LatLng(location!!.latitude,location.longitude))
                //markersDriversHashMap.put(key,marker)
            }


            override fun getKeyExited(keyExited: String) {
                mapRepository.removeMarkerInMarkerList(keyExited,markersDriversHashMap)
                mapRepository.removeMarker(markersDriversHashMap[keyExited])
            }

            override fun getKeyMoved(key: String?, location: GeoLocation) {
                mapRepository.removeMarker(markersDriversHashMap[key])
               // val marker = mapRepository.addPointInMap(LatLng(location.latitude,location.longitude))
               // markersDriversHashMap.put(key,marker)
            }

            @SuppressLint("LongLogTag")
            override fun onFailure(toString: String) {
                Log.i("Error in loading nearby drivers devices", toString)
            }

        })
    }

    fun getUpdateLocationDevice(p0: Location) {
        val currentLocationDevice = LatLng(p0.latitude,p0.longitude)
        val uidAuth = FirebaseAuth.getInstance().uid.toString()
        val locationDeviceInGeoFire = GeoLocation(p0.latitude,p0.longitude)
        val dataUpdated = mapOf("l" to locationDeviceInGeoFire)

        addMarker(currentLocationDevice)
        updateDataLocationInDataBase(uidAuth,dataUpdated)
    }
}



