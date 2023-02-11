package com.example.corridafacil.domain.services.FirebaseMenssaging


data class NotificationData(
    val uidUser: String? = null,
    val title: String? = null,
    val description: String? = null,

    val origemLatitude:Double?= null,
    val origemLongitude:Double? = null,

    val destinoLatitude:Double?= null,
    val destinoLongitude:Double? = null,
    val type:String? = null
)