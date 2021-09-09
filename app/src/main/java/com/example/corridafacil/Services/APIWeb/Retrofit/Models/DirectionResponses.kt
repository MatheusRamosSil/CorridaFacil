package com.example.corridafacil.Services.APIWeb.Retrofit.Models

import com.google.gson.annotations.SerializedName


data class DirectionResponses(
    @SerializedName("geocoded_waypoints")
    var geocodedWaypoints: List<GeocodedWaypoint?>?,
    @SerializedName("routes")
    var routes: List<Route?>?,
    @SerializedName("status")
    var status: String?

)