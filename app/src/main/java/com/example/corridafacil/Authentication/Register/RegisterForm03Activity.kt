package com.example.corridafacil.Authentication.Register

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.corridafacil.Controllers.PassageiroController
import com.example.corridafacil.Models.Passageiro
import com.example.corridafacil.R
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks
import com.google.firebase.messaging.FirebaseMessaging
import java.util.concurrent.TimeUnit


class RegisterForm03Activity : AppCompatActivity() {
    private lateinit var mCallbacks:PhoneAuthProvider.OnVerificationStateChangedCallbacks
    private lateinit var mAuth:FirebaseAuth
    private lateinit var nomeUser:String
    private lateinit var sobrenomeUser:String
    private lateinit var emailUser:String
    private lateinit var passwordUser:String
    private lateinit var telefoneUser:String
    private lateinit var fotoDoUsuario:String
    private lateinit var mVerificationId:String
    private  var ativandoUsuario:Boolean = true
    private lateinit var tokenOfDevice:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_form03)
        startVaribles()
    }

    private fun startVaribles() {
         nomeUser = intent.getStringExtra("nomeDoUsuario").toString().trim()
         sobrenomeUser = intent.getStringExtra("nomeDoSobrenome").toString().trim()
         emailUser = intent.getStringExtra("emailDoUsuario").toString().trim()
         passwordUser = intent.getStringExtra("passwordDoUsuario").toString().trim()
         telefoneUser= intent.getStringExtra("telefoneDoUsuario").toString().trim()
         fotoDoUsuario = intent.getStringExtra("fotoDoUsuario").toString().trim()
         var ativado = true
         tokenOfDevice = FirebaseMessaging.getInstance().token.toString()
        mAuth = FirebaseAuth.getInstance()
        // Initialize phone auth callbacks
        // [START phone_auth_callbacks]
        // Initialize phone auth callbacks
        // [START phone_auth_callbacks]
        mCallbacks = object : OnVerificationStateChangedCallbacks() {
            override fun onCodeSent(s: String, forceResendingToken: ForceResendingToken) {
                super.onCodeSent(s, forceResendingToken)
                mVerificationId = s
            }

            override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {
                val code = phoneAuthCredential.smsCode
                code?.let { verifyPhoneNumberWithCode(mVerificationId, it) }
            }

            override fun onVerificationFailed(e: FirebaseException) {
                Toast.makeText(this@RegisterForm03Activity, e.message, Toast.LENGTH_LONG).show()
            }
        }
        //startPhoneNumberVerification(telefoneUser);
        createAccount(emailUser, passwordUser)
    }

    private fun startPhoneNumberVerification(telefoneUser: String?) {
        // [START start_phone_auth]
        // [START start_phone_auth]
        val options = PhoneAuthOptions.newBuilder(mAuth)
                .setPhoneNumber(telefoneUser) // Phone number to verify
                .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                .setActivity(this) // Activity (for callback binding)
                .setCallbacks(mCallbacks) // OnVerificationStateChangedCallbacks
                .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }
    fun verifyPhoneNumberWithCode(verificationId: String?, code: String?) {
        // [START verify_with_code]
        val credential = PhoneAuthProvider.getCredential(verificationId, code)
        signInWithPhoneAuthCredential(credential)
        createAccount(emailUser, passwordUser)
    }

    private fun createAccount(email: String?, password: String?) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        //Log.d(TAG, "createUserWithEmail:success")
                        val user = mAuth.currentUser
                        val passageiro = Passageiro(user.uid,nomeUser,sobrenomeUser,emailUser,telefoneUser,passwordUser,fotoDoUsuario,ativandoUsuario,tokenOfDevice)
                        val passageiroController = PassageiroController()
                        passageiroController.savePassageiroInDataBase(emailUser,passageiro)
                        Toast.makeText(this,"Register successfuly", Toast.LENGTH_LONG).show()
                    } else {
                        // If sign in fails, display a message to the user.
                        //Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(baseContext, task.toString(),
                                Toast.LENGTH_SHORT).show()

                    }
                }

    }

    fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential?) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        //Log.d(FragmentActivity.TAG, "signInWithCredential:success")
                        val user = task.result.user
                        //startActivity(irParaIndexMapa)
                    } else {
                        // Sign in failed, display a message and update the UI
                        //Log.w(FragmentActivity.TAG, "signInWithCredential:failure", task.exception)
                        if (task.exception is FirebaseAuthInvalidCredentialsException) {
                            // The verification code entered was invalid
                        }
                        // [START_EXCLUDE silent]
                    }
                }
    }
}