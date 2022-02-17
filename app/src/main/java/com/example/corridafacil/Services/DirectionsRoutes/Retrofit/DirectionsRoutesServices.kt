package com.example.corridafacil.Services.DirectionsRoutes.Retrofit

import android.graphics.Color
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.corridafacil.Services.DirectionsRoutes.Retrofit.APIServices.RetrofitClient
import com.example.corridafacil.Services.DirectionsRoutes.Retrofit.Models.DirectionResponses
import com.example.corridafacil.Services.DirectionsRoutes.Retrofit.Models.InputDataRoutes
import com.example.corridafacil.Services.GoogleMapsService.Models.MapApplication
import com.example.corridafacil.mapa.Utils.ContantsMaps
import com.google.android.gms.maps.model.PolylineOptions
import com.google.maps.android.PolyUtil
import retrofit2.*
import java.lang.Exception

class DirectionsRoutesServices (private val mapApplication: MapApplication){

    suspend fun createRoutes(inputDataRoutes: InputDataRoutes): DirectionResponses? {
        val apiServices = RetrofitClient.apiServices(mapApplication.context)

        val response = apiServices.getDirections(
                    inputDataRoutes.origin,
                    inputDataRoutes.destination,
                    inputDataRoutes.rotasAlternativas,
                    inputDataRoutes.modeDriving,
                    ContantsMaps.GOOGLE_MAPS_API_KEY
                ).body()

        Log.i("Response routes", response?.routes.toString())

        return response
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


    fun showRoute(response: DirectionResponses?){
        val result = response?.routes?.get(0)?.overviewPolyline?.points
        response?.routes?.get(0)?.overviewPolyline?.points
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