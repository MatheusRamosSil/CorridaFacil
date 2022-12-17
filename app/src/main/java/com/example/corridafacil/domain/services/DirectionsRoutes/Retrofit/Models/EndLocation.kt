package com.example.corridafacil.domain.services.DirectionsRoutes.Retrofit.Models

import com.google.gson.annotations.SerializedName

data class EndLocation(
    @SerializedName("lat")
    var lat: Double?,
    @SerializedName("lng")
    var lng: Double?
)