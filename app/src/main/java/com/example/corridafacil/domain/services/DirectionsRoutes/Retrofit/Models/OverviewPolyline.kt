package com.example.corridafacil.domain.services.DirectionsRoutes.Retrofit.Models

import com.google.gson.annotations.SerializedName

data class OverviewPolyline(
    @SerializedName("points")
    var points: String?
)