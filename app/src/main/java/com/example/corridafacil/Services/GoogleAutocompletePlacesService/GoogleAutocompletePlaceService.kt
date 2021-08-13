package com.example.corridafacil.Services.GoogleAutocompletePlacesService

import android.util.Log
import com.example.corridafacil.Services.GoogleAutocompletePlacesService.Models.PlacesApplication
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.Status
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsResponse
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import java.util.*


class GoogleAutocompletePlaceService(private val autocompleteSupportFragment: AutocompleteSupportFragment,
                                     private val placesApplication: PlacesApplication){

    val query:String? = null
    val token = SessionToken.createNewToken()

    fun inicilizarAutocompletePlaces(googleAutocompletePlaceImp: GoogleAutocompletePlaceServiceImp){

        placesApplication.initilizePlaces()
        // Specify the types of place data to return.
        autocompleteSupportFragment.setPlaceFields(
            Arrays.asList(
                Place.Field.ID,
                Place.Field.NAME,
                Place.Field.ADDRESS,
                Place.Field.LAT_LNG
            )
        )
        // Set up a PlaceSelectionListener to handle the response.
        autocompleteSupportFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                googleAutocompletePlaceImp.onSuccess(place)
            }

            override fun onError(status: Status) {
                googleAutocompletePlaceImp.onError(status)
                Log.i("TAG", "An error occurred: $status")
            }
        })
    }


    fun restrigindoResultadosDeAutocompleteParaALocazicaoDoDispositivo(locazicaoDoDispositivo:LatLng){
        val placesClient = placesApplication.createPlaces()
        val filtroDelimitandoBuscaNaLocalizacoaDoDispositivos = FindAutocompletePredictionsRequest.builder()
            .setOrigin(locazicaoDoDispositivo)
            .setCountries("BR")
            .setTypeFilter(TypeFilter.CITIES)
            .setSessionToken(token)
            .setQuery(query)
            .build()
        placesClient.findAutocompletePredictions(filtroDelimitandoBuscaNaLocalizacoaDoDispositivos)
            .addOnSuccessListener { response: FindAutocompletePredictionsResponse ->
                for (prediction in response.autocompletePredictions) {
                    Log.i("O que tu faz?", prediction.placeId)
                    Log.i(
                        "O que tu faz?",
                        prediction.getSecondaryText(null).toString()
                    )
                }
            }.addOnFailureListener { exception: Exception? ->
                if (exception is ApiException) {
                    val apiException = exception as ApiException
                    Log.e(
                        "O qual erro tu mostra",
                        "Place not found: " + apiException.statusCode
                    )
                }
            }

    }

}



