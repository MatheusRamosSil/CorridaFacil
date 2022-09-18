package com.example.corridafacil.domain.services.FirebaseMenssaging.APIServices

import com.example.corridafacil.domain.services.FirebaseMenssaging.APIServices.Constants.Companion.BASE_URL
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