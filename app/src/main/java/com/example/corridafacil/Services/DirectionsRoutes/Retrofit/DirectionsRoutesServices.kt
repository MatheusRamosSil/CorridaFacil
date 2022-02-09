package com.example.corridafacil.Services.DirectionsRoutes.Retrofit

import android.graphics.Color
import android.util.Log
import com.example.corridafacil.Services.DirectionsRoutes.Retrofit.APIServices.RetrofitClient
import com.example.corridafacil.Services.DirectionsRoutes.Retrofit.Models.DirectionResponses
import com.example.corridafacil.Services.DirectionsRoutes.Retrofit.Models.InputDataRoutes
import com.example.corridafacil.Services.GoogleMapsService.Models.MapApplication
import com.example.corridafacil.mapa.Utils.ContantsMaps
import com.google.android.gms.maps.model.PolylineOptions
import com.google.maps.android.PolyUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DirectionsRoutesServices (private val mapApplication: MapApplication){


   fun createRoutes(inputDataRoutes: InputDataRoutes, directionsRoutesImp:DirectionsRoutesImp) {
       val apiServices = RetrofitClient.apiServices(mapApplication.context)

       apiServices.getDirections(
           inputDataRoutes.origin,
           inputDataRoutes.destination,
           inputDataRoutes.rotasAlternativas,
           inputDataRoutes.modeDriving,
           ContantsMaps.GOOGLE_MAPS_API_KEY
       )
            .enqueue(object : Callback<DirectionResponses> {
                override fun onResponse(call: Call<DirectionResponses>,
                                        response: Response<DirectionResponses>
                ) {
                    directionsRoutesImp.onSuccess(response)
                    Log.i("Response mensage", response.body()?.routes.toString())
                }

                override fun onFailure(call: Call<DirectionResponses>, t: Throwable) {
                    directionsRoutesImp.onFailure(t.localizedMessage)
                    Log.i("Error routes", t.localizedMessage)
                }
            })
    }


    fun showMultiplesRoutes(response: Response<DirectionResponses>) {
        val result = response.body()?.routes
        if (result != null) {
            for (anotherRoutes: Int in result.indices){
                val multipleRoutes = result.get(anotherRoutes)?.overviewPolyline?.points
                drawPolyline(multipleRoutes)

            }
        }

    }


    fun showRoute(response: Response<DirectionResponses>){
        val result = response.body()?.routes?.get(0)?.overviewPolyline?.points
        response.body()?.routes?.get(0)?.overviewPolyline?.points
        drawPolyline(result)
    }



    fun drawPolyline(routes: String?) {
        if(routes != null){
            val polyline = PolylineOptions()
                .addAll(PolyUtil.decode(routes))
                .width(8f)
                .color(Color.RED)
                .geodesic(true)
            mapApplication.mMap.addPolyline(polyline)
        }
    }

}