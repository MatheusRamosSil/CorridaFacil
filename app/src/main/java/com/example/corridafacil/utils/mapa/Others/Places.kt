package com.example.corridafacil.domain.services.GoogleAutocompletePlacesService

import android.content.Context
import com.example.corridafacil.utils.mapa.ContantsMaps
import com.google.android.libraries.places.api.Places

fun Context.initilizePlaces() = Places.initialize(this, ContantsMaps.GOOGLE_MAPS_API_KEY)

fun Context.createNewPlacesClient() = Places.createClient(this)