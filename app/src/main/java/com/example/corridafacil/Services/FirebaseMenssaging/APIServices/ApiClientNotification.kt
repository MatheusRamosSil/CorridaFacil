package com.example.corridafacil.Services.FirebaseMenssaging.APIServices

import android.content.Context
import com.example.corridafacil.R
import com.example.corridafacil.Services.FirebaseMenssaging.APIServices.Constants.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClientNotification {

    companion object {
        private val retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        val api by lazy {
            retrofit.create(ApiInterfaceNotification::class.java)
        }
    }
}