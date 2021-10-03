package com.example.corridafacil.Services.DirectionsRoutes.Retrofit

import com.example.corridafacil.Services.DirectionsRoutes.Retrofit.Models.DirectionResponses
import retrofit2.Response

interface DirectionsRoutesImp {
     fun onSuccess(response: Response<DirectionResponses>)
     fun onFailure(localizedMessage: String?)
}