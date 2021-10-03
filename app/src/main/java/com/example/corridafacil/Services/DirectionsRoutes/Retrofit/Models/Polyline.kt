package com.example.corridafacil.Services.DirectionsRoutes.Retrofit.Models

import com.google.gson.annotations.SerializedName

data class Polyline(
    @SerializedName("points")
    var points: String?
)