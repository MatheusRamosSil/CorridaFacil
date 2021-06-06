package com.example.corridafacil

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.corridafacil.Authentication.AuthenticatonEmail
import com.example.corridafacil.Authentication.Register.Formularios.PrimeiroFormulario
import com.example.corridafacil.Authentication.ui.login.LoginActivity
import com.example.corridafacil.Mapa.MapsActivity

class WelcomeActivity : AppCompatActivity() {

    private lateinit var buttonNextPage: Button
    private var authenticationEmail = AuthenticatonEmail()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)


        if(authenticationEmail.checkIfEmailVerified()){
            val irParaMapa= Intent(this, MapsActivity::class.java)
            startActivity(irParaMapa)
        }
        buttonNextPage = findViewById(R.id.button5)
        buttonNextPage.setOnClickListener { startActivity(Intent(this,LoginActivity::class.java)) }

        buttonNextPage = findViewById(R.id.button6)
        buttonNextPage.setOnClickListener { startActivity(Intent(this,PrimeiroFormulario::class.java)) }

    }
}