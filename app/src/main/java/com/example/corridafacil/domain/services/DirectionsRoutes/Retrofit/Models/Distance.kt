package com.example.corridafacil.domain.services.DirectionsRoutes.Retrofit.Models

import com.google.gson.annotations.SerializedName

data class Distance(
    @SerializedName("text")
    var text: String?,
    @SerializedName("value")
    var value: Int?
)