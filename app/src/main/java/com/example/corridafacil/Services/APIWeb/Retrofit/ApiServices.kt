package com.example.corridafacil.Services.APIWeb.Retrofit

import com.example.corridafacil.Services.APIWeb.Retrofit.Models.DirectionResponses
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServices {
    @GET("maps/api/directions/json")
    fun getDirections(@Query ("origin") origin:String,
                      @Query("destination") destination: String,
                      @Query("key") apiKey:String): Call<DirectionResponses>
}
