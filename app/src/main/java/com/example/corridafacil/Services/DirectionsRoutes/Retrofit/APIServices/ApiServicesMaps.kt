package com.example.corridafacil.Services.DirectionsRoutes.Retrofit.APIServices

import com.example.corridafacil.Services.DirectionsRoutes.Retrofit.Models.DirectionResponses
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServicesMaps {
    @GET("maps/api/directions/json")
    fun getDirections(@Query ("origin") origin:String?,
                      @Query("destination") destination: String?,
                      @Query("alternatives") rotasAlternativas:Boolean?,
                      @Query("mode") modeDriving:String?,
                      @Query("key") apiKey:String): Call<DirectionResponses>
}
