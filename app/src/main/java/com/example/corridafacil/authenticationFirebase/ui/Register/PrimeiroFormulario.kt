package com.example.corridafacil.authenticationFirebase.ui.Register

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.corridafacil.R
import com.hbb20.CountryCodePicker

class PrimeiroFormulario : AppCompatActivity() {
    private lateinit var getCountryCodePicker : CountryCodePicker
    private lateinit var getTelefone:EditText
    private lateinit var buttonNextPage: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_form02)
        startVariables()
    }

    private fun startVariables() {
         getCountryCodePicker = findViewById(R.id.ccp)
         getTelefone = findViewById(R.id.editTextPersonPhone)
         buttonNextPage = findViewById(R.id.button2)
         buttonNextPage.setOnClickListener(this::nextPage)
    }

    fun nextPage(view: View){
        getCountryCodePicker.registerCarrierNumberEditText(getTelefone)
        val intent = Intent(this, SegundoFormulario::class.java)
        intent.putExtra("telefone",getCountryCodePicker.fullNumberWithPlus)
        startActivity(intent)

    }

}