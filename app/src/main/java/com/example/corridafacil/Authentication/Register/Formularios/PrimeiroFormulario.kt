package com.example.corridafacil.Authentication.Register.Formularios

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.corridafacil.Models.Passageiro
import com.example.corridafacil.R
import com.hbb20.CountryCodePicker

class PrimeiroFormulario : AppCompatActivity() {
    private lateinit var getCountryCodePicker : CountryCodePicker
    private lateinit var getTelefone:EditText
    private var passageiroCompanion = Passageiro

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
         passageiroCompanion.passageiro.telefone = getCountryCodePicker.fullNumberWithPlus
        startActivity(Intent(this,SegundoFormulario::class.java))
    }

}