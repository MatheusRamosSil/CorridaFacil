package com.example.corridafacil.Services.FirebaseMenssaging

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlinx.coroutines.tasks.await

class FirebaseMenssagingServices : FirebaseMessagingService()  {

    override fun onMessageReceived(p0: RemoteMessage) {
        super.onMessageReceived(p0)
    }

    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
    }

    suspend fun generateTokenFCM(): String {
        return FirebaseMessaging.getInstance().token.await().toString()
    }
}