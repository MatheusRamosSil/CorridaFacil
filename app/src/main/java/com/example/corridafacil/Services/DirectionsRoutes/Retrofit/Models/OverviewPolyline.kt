package com.example.corridafacil.Services.DirectionsRoutes.Retrofit.Models

import com.google.gson.annotations.SerializedName

data class OverviewPolyline(
    @SerializedName("points")
    var points: String?
)