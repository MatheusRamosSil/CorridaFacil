package com.example.corridafacil.Services.FirebaseMenssaging.APIServices

import com.example.corridafacil.Services.FirebaseMenssaging.APIServices.Constants.Companion.CONTENT_TYPE
import com.example.corridafacil.Services.FirebaseMenssaging.APIServices.Constants.Companion.SERVER_KEY
import com.example.corridafacil.Services.FirebaseMenssaging.PushNotification
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiInterfaceNotification {
   /* @Headers("Content-Type:application/json",
        "Authorization:key=AAAAbpdMCPg:APA91bH7B14X7v7r_vh83F1Odj5Snv3ZllKSv6Xlq6d8kDpYXjBugKFC6kWJpWn498gPmVZr5ogmZZe1eM3TaHqo8KIr9M9eWB6bNhygbox-umTPGwL2wYWVjkfPEVVx1Tx-SQ8Ihglk")
    @POST("fcm/send")
    suspend fun sendNotification(@Body body: Sender?): Response<ResponseBody>


    */
   @Headers("Authorization: key=$SERVER_KEY", "Content-Type:$CONTENT_TYPE")
   @POST("fcm/send")
   suspend fun postNotification(
       @Body notification: PushNotification
   ): Response<ResponseBody>
}