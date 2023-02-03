package com.example.corridafacil.domain.services.FirebaseMenssaging

import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.Test

class FirebaseMenssagingServicesTest
{

    @Test
    fun sendNotification() = runBlocking {
        ;

        val firebaseMenssagingServices=FirebaseMenssagingServices()

        firebaseMenssagingServices.sendNotification(
            "f-Z_dBG8T6y40-CgdyuumP:APA91bGKPcV9BbemLkJDMI470sbeVeaRsZHdxAdiZmNhuuZeT9yhMEhDg36-RcurEe4cs82NFxSn2KhASDT_zsv8mKk8i4hR9TzNFnofawTmKh7kOhyVWuC88WW_-JNIMLnx6Uu_lacl",
            "op"
        )

    }
}