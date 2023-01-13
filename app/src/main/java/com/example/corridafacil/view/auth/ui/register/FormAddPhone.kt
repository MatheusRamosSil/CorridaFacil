package com.example.corridafacil.view.auth.ui.register

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.corridafacil.R
import com.example.corridafacil.utils.validators.ValidatorsFieldsForms.isValidPhoneNumber
import com.example.corridafacil.utils.validators.errorMessageUI.MessageErrorForBadFormatInFormsFields
import com.example.corridafacil.utils.validators.errorMessageUI.ShowMessageErrorBadFormart.showMensageErrorFormaBad
import com.hbb20.CountryCodePicker

class FormAddPhone : AppCompatActivity() {

    private lateinit var codeCountryCodePicker: CountryCodePicker
    private lateinit var numeroTelefoneDigitado : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_add_phone)

    }

    override fun onStart() {
        super.onStart()
        numeroTelefoneDigitado = findViewById(R.id.editTextPersonPhone)
        codeCountryCodePicker = findViewById(R.id.ccp)

    }

    override fun onResume() {
        super.onResume()
        codeCountryCodePicker.registerCarrierNumberEditText(numeroTelefoneDigitado)


    }

    fun nextPage(view: View) {
        val formatFieldPhoneNumber = isValidPhoneNumber(codeCountryCodePicker.fullNumberWithPlus)

        validarNumeroTelefone(formatFieldPhoneNumber)
    }

    private fun validarNumeroTelefone(formatFieldPhoneNumber: Boolean) {

        if(formatFieldPhoneNumber){
            val telefone = codeCountryCodePicker.fullNumberWithPlus
            toPageFormAddCode(telefone)

        }else{
            formatFieldPhoneNumber.showMensageErrorFormaBad(numeroTelefoneDigitado,
                MessageErrorForBadFormatInFormsFields.PHONE_BAD_FORMAT)
        }

    }

    private fun toPageFormAddCode(telefone: String){
        val intent = Intent(this, FormAddCodeSms::class.java)
        intent.putExtra("numeroTelefoneDoUsuario", telefone)
        startActivity(intent)
    }


}