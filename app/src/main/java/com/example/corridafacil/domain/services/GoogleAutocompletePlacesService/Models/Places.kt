package com.example.corridafacil.domain.services.GoogleAutocompletePlacesService.Models

import android.content.Context
import com.google.android.libraries.places.api.Places
import com.example.corridafacil.utils.mapa.ContantsMaps.GOOGLE_MAPS_API_KEY

class PlacesApplication (private val context: Context){

    fun initilizePlaces() = Places.initialize(context, GOOGLE_MAPS_API_KEY)

    fun createPlaces() = Places.createClient(context)
}