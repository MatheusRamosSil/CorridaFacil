package com.example.corridafacil.authenticationFirebase.Utils

import android.content.Context
import android.content.Intent
import com.example.corridafacil.mapa.ui.MapsActivity


    fun Context.irParaPaginaDoMapa() = Intent(this, MapsActivity::class.java)
        .also {it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
         startActivity(it)
        }

    fun Context.loginPage() = Intent(this,com.example.corridafacil.authenticationFirebase.ui.LoginActivity::class.java)
    .also {it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(it)
    }


