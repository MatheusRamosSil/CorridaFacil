package com.example.corridafacil.Authentication.Register

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.corridafacil.R
import com.hbb20.CountryCodePicker

class RegisterForm02Activity : AppCompatActivity() {
    private lateinit var getCountryCodePicker : CountryCodePicker
    private lateinit var getTelefone:EditText
    val positiveButtonClick = { dialog: DialogInterface, which: Int ->
        Toast.makeText(applicationContext, android.R.string.no, Toast.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_form02)
        startVariables()
        val teste = intent.getStringExtra("nomeDoUsuario")+"\n"+intent.getBundleExtra("sobrenomeDoUsuario").toString()+"\n"+intent.getStringExtra("passwordDoUsuario").toString()+"\n"+intent.getStringExtra("emailDoUsuario")+"\n"+intent.getStringExtra("fotoDoUsuario")
        showTest(teste)
    }

    private fun showTest(teste: String) {
        val builder = AlertDialog.Builder(this)

        with(builder)
        {
            setTitle("Androidly Alert")
            setMessage(teste)
            setPositiveButton("OK", DialogInterface.OnClickListener(function = positiveButtonClick))
            show()
        }
    }

    private fun startVariables() {
         getCountryCodePicker = findViewById(R.id.ccp)
         getTelefone = findViewById(R.id.editTextPersonPhone)
    }


    fun nextPage(view: View){
        getCountryCodePicker.registerCarrierNumberEditText(getTelefone)
        var telefoneDoUsario = getCountryCodePicker.fullNumberWithPlus
        val irParaAProximaPagina = Intent(this, RegisterForm03Activity::class.java).apply {
            putExtra("telefoneDoUsuario", telefoneDoUsario)
            putExtra("nomeDoUsuario",intent.getStringExtra("nomeDoUsuario"))
            putExtra("sobrenomeDoUsuario",intent.getStringExtra("sobrenomeDoUsuario"))
            putExtra("emailDoUsuario",intent.getStringExtra("emailDoUsuario"))
            putExtra("passwordDoUsuario",intent.getStringExtra("passwordDoUsuario"))
            putExtra("fotoDoUsuario",intent.getStringExtra("fotoDoUsuario"))
        }
        startActivity(irParaAProximaPagina)
    }
}