package com.example.corridafacil.domain.services.FirebaseMenssaging

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_HIGH
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.example.corridafacil.R
import com.example.corridafacil.domain.services.FirebaseMenssaging.APIServices.ApiClientNotification
import com.example.corridafacil.domain.services.FirebaseMenssaging.utils.ConstantsFCM
import com.example.corridafacil.view.mapa.ui.MapsActivity
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.firebase.messaging.ktx.remoteMessage
import kotlinx.coroutines.tasks.await
import kotlin.random.Random

@SuppressLint("MissingFirebaseInstanceTokenRefresh")
class FirebaseMenssagingServices : FirebaseMessagingService()  {
    private val TAG = "MyFirebaseMsgService"
    private val CHANNEL_ID = "my_channel"

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if(message.data["type"].equals("call_to_run")){

            Log.w("Dado motorista", "${message.data.get("requestAccepted")}\n ")
            val data = message.data.get("dataCorrida")
            return
        }
        pushNotification(notificationManager, message)

    }

    private fun pushNotification(notificationManager: NotificationManager,remoteMessage: RemoteMessage){

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            createNotificationChannel(notificationManager)
        }

        if(remoteMessage.data["type"].equals("simple_notification")){

            val notificationID = Random.nextInt()
            val intent = Intent(this, MapsActivity::class.java)

            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            val notification = NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle(remoteMessage.data["title"])
                .setContentText(remoteMessage.data["message"])
                .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark)
                .setAutoCancel(true)
                .build()

            notificationManager.notify(notificationID, notification)
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(notificationManager: NotificationManager) {

        val channelName = "channelName"
        val channel = NotificationChannel(CHANNEL_ID, channelName, IMPORTANCE_HIGH).apply {
            description = "My channel description"
            enableLights(true)
            lightColor = Color.GREEN
        }
        notificationManager.createNotificationChannel(channel)
    }

    override fun onNewToken(p0: String) {
        super.onNewToken(p0)

        Log.w("New token", p0)
    }

    suspend fun generateTokenFCM(): String {
        return FirebaseMessaging.getInstance().token.await().toString()
    }

    suspend fun sendNotification(
        pushNotification: PushNotification
    ){
        try {
            val response = ApiClientNotification.api
            response.postNotification(pushNotification)
        }catch(e: Exception) {
            Log.e(TAG, e.toString())
        }

    }


}