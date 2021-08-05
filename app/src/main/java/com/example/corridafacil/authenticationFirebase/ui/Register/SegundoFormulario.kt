package com.example.corridafacil.authenticationFirebase.ui.Register

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.corridafacil.R
import com.example.corridafacil.Services.AuthenticationFirebaseSevice.AuthenticationPhoneFirebaseService
import com.example.corridafacil.authenticationFirebase.Repository.PhoneRepository
import com.example.corridafacil.authenticationFirebase.viewModel.PhoneViewModel
import com.example.corridafacil.authenticationFirebase.viewModel.PhoneViewModelFactory
import com.example.corridafacil.databinding.ActivityRegisterForm03Binding
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider


class SegundoFormulario : AppCompatActivity() {

    private lateinit var telefonePassageiro: String
    private lateinit var code:String
    private  var mAuth = FirebaseAuth.getInstance()
    private lateinit var phoneAuthToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var binding: ActivityRegisterForm03Binding
    lateinit var phoneViewModel: PhoneViewModel

    val mCallbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
            super.onCodeSent(p0, p1)
            code = p0
            phoneAuthToken = p1
            Log.i("TESTE_CODE", "TESTANDO_Code " + code)
            binding.viewmodel?.code = code

        }

        override fun onVerificationCompleted(p0: PhoneAuthCredential) {
            val code = p0.smsCode
            if (code != null) {
                binding.codeVerification.setText(code)
                binding.viewmodel?.smsCode = code
                checandoNumeroDeTelefoneComSignEirParaProximaPagina(p0)
            }
        }

        override fun onVerificationFailed(p0: FirebaseException) {
            Toast.makeText(this@SegundoFormulario,"Error "+p0.message.toString(),
                            Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_form03)
        binding = ActivityRegisterForm03Binding.inflate(layoutInflater)
        setContentView(binding.root)

        phoneViewModel =
            ViewModelProvider(this,PhoneViewModelFactory(
                PhoneRepository(
                AuthenticationPhoneFirebaseService(mCallbacks))
            ))
                .get(PhoneViewModel::class.java)
        binding.viewmodel = phoneViewModel

        startVaribles()

    }

    private fun startVaribles() {
        telefonePassageiro = intent.getStringExtra("telefone").toString()

        binding.viewmodel?.authPhone?.activity = SegundoFormulario()
        binding.viewmodel?.authPhone?.telefoneDoUsuario = telefonePassageiro
        binding.viewmodel?.startPhoneNumberVerification()

        binding.button4.setOnClickListener(this::buttonNextPage)
        habilitarButtonResendSmsCodeApos60segundos()

    }

    fun buttonNextPage(v:View) {
        binding.viewmodel?.code = code
        binding.viewmodel?.smsCode = binding.codeVerification.text.toString()

        val credential = binding.viewmodel?.verifyPhoneNumberWithCode()

        if (credential != null) {
            checandoNumeroDeTelefoneComSignEirParaProximaPagina(credential)
        }
    }

    fun habilitarButtonResendSmsCodeApos60segundos(){
        binding.resendSmsCode.isEnabled = false
        Handler().postDelayed(
            { binding.resendSmsCode.setEnabled(true) }, 10000//Specific time in milliseconds
        )
        binding.resendSmsCode.setOnClickListener {
           reenviarCodigoSMS()
        }
    }

    fun reenviarCodigoSMS(){
        binding.viewmodel?.phoneAuthToken = phoneAuthToken
        binding.viewmodel?.resendVerificationCode()
    }

    private fun checandoNumeroDeTelefoneComSignEirParaProximaPagina(credential: PhoneAuthCredential) {
        Log.d(ContentValues.TAG,"Credentials phone" + credential)
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful){
                  irParaProximaPagina()
                }else{
                    Log.d(ContentValues.TAG,"Falha no envio")
                    Log.d(ContentValues.TAG, "signInWithCredential:failure ", task.getException());
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                        Toast.makeText(this@SegundoFormulario,"Erro na verificação",
                            Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }

    fun irParaProximaPagina(){
        val intent = Intent(this, TerceiroFormulario::class.java)
        intent.putExtra("telefone",telefonePassageiro)
        startActivity(intent)
    }
}