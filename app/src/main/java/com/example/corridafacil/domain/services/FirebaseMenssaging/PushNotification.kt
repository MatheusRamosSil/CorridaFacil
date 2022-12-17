package com.example.corridafacil.domain.services.FirebaseMenssaging


data class PushNotification(
    val data: NotificationData,
    val to: String
)