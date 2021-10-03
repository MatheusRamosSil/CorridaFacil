package com.example.corridafacil.Services.DirectionsRoutes.Retrofit.APIServices

import android.content.Context
import com.example.corridafacil.R
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    fun apiServices(context: Context): ApiServicesMaps {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(context.resources.getString(R.string.base_url))
            .build()

        return retrofit.create<ApiServicesMaps>(ApiServicesMaps::class.java)
    }
}