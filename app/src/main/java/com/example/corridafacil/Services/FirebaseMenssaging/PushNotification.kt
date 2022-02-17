package com.example.corridafacil.Services.FirebaseMenssaging

import android.app.Notification
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.annotations.SerializedName

data class PushNotification(
    val data: NotificationData,
    val to: String
)