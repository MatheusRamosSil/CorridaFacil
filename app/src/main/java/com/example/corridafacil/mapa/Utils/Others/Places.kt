package com.example.corridafacil.Services.GoogleAutocompletePlacesService

import android.content.Context
import com.example.corridafacil.mapa.Utils.ContantsMaps
import com.google.android.libraries.places.api.Places

fun Context.initilizePlaces() = Places.initialize(this, ContantsMaps.GOOGLE_MAPS_API_KEY)

fun Context.createNewPlacesClient() = Places.createClient(this)