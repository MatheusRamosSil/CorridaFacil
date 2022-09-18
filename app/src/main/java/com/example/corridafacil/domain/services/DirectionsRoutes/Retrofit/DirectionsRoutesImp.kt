package com.example.corridafacil.domain.services.DirectionsRoutes.Retrofit

import com.example.corridafacil.domain.services.DirectionsRoutes.Retrofit.Models.DirectionResponses
import retrofit2.Response

interface DirectionsRoutesImp {
     fun onSuccess(response: Response<DirectionResponses>)
     fun onFailure(localizedMessage: String?)
}