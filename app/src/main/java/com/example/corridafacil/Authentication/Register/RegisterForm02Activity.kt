package com.example.corridafacil.Authentication.Register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.example.corridafacil.R
import com.hbb20.CountryCodePicker

class RegisterForm02Activity : AppCompatActivity() {
    private lateinit var getCountryCodePicker : CountryCodePicker
    private lateinit var getTelefone:EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_form02)
        startVariables()
    }

    private fun startVariables() {
         getCountryCodePicker = findViewById(R.id.ccp)
         getTelefone = findViewById(R.id.editTextPersonPhone)
    }

    fun nextPage(view: View){
        getCountryCodePicker.registerCarrierNumberEditText(getTelefone)
        var telefoneDoUsario = getCountryCodePicker.fullNumberWithPlus
        val irParaAProximaPagina = Intent(this, RegisterForm03Activity::class.java).apply {
            putExtra("code", telefoneDoUsario)
        }
        startActivity(irParaAProximaPagina)
    }
}