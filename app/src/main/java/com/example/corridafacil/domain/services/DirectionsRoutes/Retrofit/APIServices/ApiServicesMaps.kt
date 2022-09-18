package com.example.corridafacil.domain.services.DirectionsRoutes.Retrofit.APIServices

import com.example.corridafacil.domain.services.DirectionsRoutes.Retrofit.Models.DirectionResponses
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServicesMaps {
    @GET("maps/api/directions/json")
    suspend fun getDirections(@Query ("origin") origin:String?,
                      @Query("destination") destination: String?,
                      @Query("alternatives") rotasAlternativas:Boolean?,
                      @Query("mode") modeDriving:String?,
                      @Query("key") apiKey:String): Response<DirectionResponses>
}
