package com.example.corridafacil.view.auth.ui.register

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.corridafacil.view.auth.viewModel.PhoneViewModel
import com.example.corridafacil.utils.responsive.rememberWindowSize
import com.example.corridafacil.utils.validators.ValidatorsFieldsForms.isValidCodeSMS
import com.example.corridafacil.view.auth.ui.ui.theme.CorridaFacilTheme
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class FormAddCodeSms : AppCompatActivity() {

    private lateinit var viewModelPhone: PhoneViewModel
    private lateinit var numeroTelefoneDoUsuario: String
    private lateinit var codigoSmsDigitadoPeloUsuario: EditText

    private  lateinit var codeSms: String
    private lateinit var auth: FirebaseAuth

    lateinit var authPhone :AuthenticationPhone
    private lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.w("FormCode", "entrou")
      /*  setContent {
            CorridaFacilTheme {
                val window = rememberWindowSize()
                navController = rememberNavController()
                CodeOTPScreen(
                    window = window,
                    navController = navController,
                    registeCodeOTPActivity = this
                )
            }
        }

       */

        auth = Firebase.auth


        authPhone = MockAuthenticationPhoneFirbaseServiceImpl()

        val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verification without
                //     user action.
                Log.w("Deu certo", "entrou")
                Log.w("Valid phone", "onVerificationCompleted:${credential.smsCode}")

            }

            override fun onVerificationFailed(e: FirebaseException) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                Log.w("Deu errado", "entrou")
                Log.w("Error valid phone", "onVerificationFailed", e)

                if (e is FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                } else if (e is FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                }

                // Show a message and update the UI
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                Log.w("Mais ou menos", "entrou")
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                codeSms = "123456"
                val credential = PhoneAuthProvider.getCredential(verificationId!!, codeSms)
                Log.w("Code sent", "onCodeSent:$verificationId")
                //signInWithPhoneAuthCredential(credential)
                authPhone.validateCodeSMSWithPhoneAuthCredential(credential,this@FormAddCodeSms)
            }

        }

        val numberTest ="+5511123456789"
        val numberReal = "+5587999690335"

        authPhone.verifyPhoneNumber(numberTest,this,callbacks)


    }


    /*
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


     */
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
            //adicionaCodigoSmsManual()
        }else{
            codigoSmsDigitadoPeloUsuario.error = "Codigo invalido"
        }
    }

    /*
    private fun adicionaCodigoSmsManual() {
        viewModelPhone.verificationId.observe(this, Observer { idVerification ->

            val codeSMS = codigoSmsDigitadoPeloUsuario.text.toString()
            val newCredentials = binding.viewmodel?.criarNovasCredenciaisDeAutencacaoTelefone(codeSMS)

            if (newCredentials != null) {
                binding.viewmodel?.verificarSeNumeroDeTelefoneValidoComLoginPhone(newCredentials)
            }
        })
    }

     */



}

class MockAuthenticationPhoneFirbaseServiceImpl():AuthenticationPhone{
    private val auth = Firebase.auth

    override fun verifyPhoneNumber(
        phoneNumber: String,
        activity: Activity,
        callbackPhoneAuthProvider: PhoneAuthProvider.OnVerificationStateChangedCallbacks, ) {
        Log.w("Chamou", "verifyNumber")

        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)
            .setActivity(activity)
            .setCallbacks(callbackPhoneAuthProvider)
            .setTimeout(10L,TimeUnit.SECONDS)
            .build()

        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    override fun validateCodeSMSWithPhoneAuthCredential(credential: PhoneAuthCredential,
                                                        activity: Activity
                                                        ) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(activity){ task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.w("Success login phone", "signInWithCredential:success")

                    val user = task.result?.user
                } else {
                    // Sign in failed, display a message and update the UI
                    Log.w("Error login phone", "signInWithCredential:failure", task.exception)
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                    }
                    // Update UI
                }

            }
    }

}

interface AuthenticationPhone{
    fun verifyPhoneNumber(phoneNumber:String, activity : Activity, callbackPhoneAuthProvider: PhoneAuthProvider.OnVerificationStateChangedCallbacks)
    fun validateCodeSMSWithPhoneAuthCredential(credential: PhoneAuthCredential,activity: Activity)
}