package com.example.corridafacil.domain.services.FirebaseMenssaging


data class PushNotification(
    val to: String,
    val data: NotificationData? = null,

)