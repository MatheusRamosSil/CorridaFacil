package com.example.corridafacil.view.mapa.viewModel

import com.google.android.gms.maps.model.LatLng
import java.lang.Exception

sealed class ResultLocation{
    data class LastLocationDevice(val lastLocationDevice: LatLng) : ResultLocation()
    data class Error(val exception: Exception) : ResultLocation()
}