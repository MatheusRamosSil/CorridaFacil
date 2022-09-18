package com.example.corridafacil.view.auth.ui.register

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.corridafacil.R
import com.example.corridafacil.domain.services.AuthenticationFirebaseSevice.Phone.AuthenticationPhoneFirebaseService
import com.example.corridafacil.authenticationFirebase.repository.PhoneRepositoryImpl
import com.example.corridafacil.view.auth.viewModel.PhoneViewModel
import com.example.corridafacil.view.auth.viewModel.factories.ViewModelPhoneFactory
import com.example.corridafacil.databinding.ActivityFormAddCodeSmsBinding
import com.example.corridafacil.utils.validators.ValidatorsFieldsForms.isValidCodeSMS

class FormAddCodeSms : AppCompatActivity() {
    private lateinit var binding: ActivityFormAddCodeSmsBinding
    private lateinit var viewModelPhone: PhoneViewModel
    private lateinit var numeroTelefoneDoUsuario: String
    private lateinit var codigoSmsDigitadoPeloUsuario: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_add_code_sms)
        binding = ActivityFormAddCodeSmsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModelPhone =
            ViewModelProvider(this, ViewModelPhoneFactory(PhoneRepositoryImpl(
                AuthenticationPhoneFirebaseService()
            ))
            )
                .get(PhoneViewModel::class.java)
        binding.viewmodel = viewModelPhone

    }

    override fun onStart() {
        super.onStart()
        numeroTelefoneDoUsuario = intent.getStringExtra("numeroTelefoneDoUsuario").toString()

        viewModelPhone.iniciarVerificacaoNumeroTelefone(numeroTelefoneDoUsuario,this)
        codigoSmsDigitadoPeloUsuario = findViewById(R.id.codeVerification)
        binding.button4.setOnClickListener(this::toNextPage)
        habilitarButtonResendSmsCodeApos60segundos(numeroTelefoneDoUsuario)

    }


    override fun onResume() {
        super.onResume()

        viewModelPhone.getCredentialsValidad.observe(this, Observer { credential ->
            Log.w("Code sms: ", credential.smsCode.toString())
            binding.codeVerification.setText(credential.smsCode)
            binding.viewmodel?.verificarSeNumeroDeTelefoneValidoComLoginPhone(credential)
        })

        viewModelPhone.statusLogin.observe(this, Observer { status ->
            if (status){
                toPageFormAddEmail(numeroTelefoneDoUsuario)
            }
        })

        viewModelPhone.menssageErrorLogin.observe(this, Observer {
            Toast.makeText(this,it.message, Toast.LENGTH_SHORT).show()
        })

    }

    private fun toPageFormAddEmail(valuesFieldsForms: String){
        val intent = Intent(this, FormAddEmail::class.java)
        intent.putExtra("numeroTelefoneDoUsuario", valuesFieldsForms)
        startActivity(intent)
    }

    fun toNextPage(view: View){
        validarCodigoSMS(codigoSmsDigitadoPeloUsuario.text.toString())
    }

    private fun validarCodigoSMS(codigoSmsString: String) {
        if (isValidCodeSMS(codigoSmsString)){
            adicionaCodigoSmsManual()
        }else{
            codigoSmsDigitadoPeloUsuario.error = "Codigo invalido"
        }
    }

    private fun adicionaCodigoSmsManual() {
        viewModelPhone.verificationId.observe(this, Observer { idVerification ->

            val codeSMS = codigoSmsDigitadoPeloUsuario.text.toString()
            val newCredentials = binding.viewmodel?.criarNovasCredenciaisDeAutencacaoTelefone(codeSMS)

            if (newCredentials != null) {
                binding.viewmodel?.verificarSeNumeroDeTelefoneValidoComLoginPhone(newCredentials)
            }
        })
    }

    fun habilitarButtonResendSmsCodeApos60segundos(numeroTelefoneDoUsuario: String) {
        binding.resendSmsCode.isEnabled = false
        Handler().postDelayed(
            { binding.resendSmsCode.setEnabled(true) }, 60000//Specific time in milliseconds
        )
        binding.resendSmsCode.setOnClickListener {
            binding.viewmodel?.reenviarCodigoDeVerificacao(numeroTelefoneDoUsuario,this)
        }
    }

}