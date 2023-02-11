package com.example.corridafacil.domain.services.FirebaseMenssaging

import com.example.corridafacil.domain.services.FirebaseMenssaging.utils.ConstantsFCM
import kotlinx.coroutines.runBlocking

import org.junit.Test

class FirebaseMenssagingServicesTest
{

    @Test
    fun sendNotification() = runBlocking {


        val firebaseMenssagingServices=FirebaseMenssagingServices()
        val toDriverTokenFCM = "f-Z_dBG8T6y40-CgdyuumP:APA91bG1IA-3b6TiCXO6oF0hFWPTUYo9hzwd9SEnoZYYfrUyKlVfMW8clkbGSCu1CwFxhaB7bkoiPoXeIT2XNrUbQGIqMglxOkhTBTfAmA8I0gBLXD3ErZgaxCh-CSKRzE2WF9qkiJlM"
        val dataNotification = NotificationData(
            "testando",
            "Nova mensagem",
            "Iniciou uma conversa",
            0.9008,
            2.00895,
            5.700,
            9.9000,
            ConstantsFCM.SIMPLE_NOTIFICATION
        )
        val dataToCallRun = NotificationData(
            "tes",
            "Aguardando sua confirmação",
            "Temos uma nova viagem para você!",
            -7.591851,
            -37.5569748,
            -7.6367088,
            -37.8848102,
            ConstantsFCM.CALL_TO_RUN
        )
        val pushNotification = PushNotification(toDriverTokenFCM,dataToCallRun)
        firebaseMenssagingServices.sendNotification(pushNotification)

    }
}