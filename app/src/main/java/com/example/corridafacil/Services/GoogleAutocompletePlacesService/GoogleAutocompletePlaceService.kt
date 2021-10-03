package com.example.corridafacil.Services.GoogleAutocompletePlacesService

import android.util.Log
import com.example.corridafacil.Services.GoogleAutocompletePlacesService.Models.PlacesApplication
import com.google.android.gms.common.api.Status
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.RectangularBounds
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import java.util.*


class GoogleAutocompletePlaceService(private val autocompleteSupportFragment: AutocompleteSupportFragment,
                                     private val placesApplication: PlacesApplication){

    private var tiposdeDadosRetornados = Arrays.asList(Place.Field.ID,
                                                Place.Field.NAME,
                                                Place.Field.ADDRESS,
                                                Place.Field.LAT_LNG)


    fun inicilizarAutocompletePlaces(googleAutocompletePlaceServiceImp: GoogleAutocompletePlaceServiceImp){

        placesApplication.initilizePlaces()
        // Specify the types of place data to return.
        autocompleteSupportFragment.setPlaceFields(tiposdeDadosRetornados)
                                   .setCountry("BR")
                                   .setTypeFilter(TypeFilter.GEOCODE)

        // Set up a PlaceSelectionListener to handle the response.
        autocompleteSupportFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                googleAutocompletePlaceServiceImp.onSuccess(place)
            }

            override fun onError(status: Status) {
                googleAutocompletePlaceServiceImp.onError(status)
                Log.i("TAG", "An error occurred: $status")
            }
        })
    }


}



