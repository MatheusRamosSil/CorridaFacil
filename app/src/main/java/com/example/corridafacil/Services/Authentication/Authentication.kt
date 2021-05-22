package com.example.corridafacil.Services.Authentication

import android.app.Activity
import android.content.ContentValues.TAG
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.corridafacil.Controller.PassageiroControllers
import com.example.corridafacil.Exceptions.MensagesSystemApp
import com.example.corridafacil.Models.Passageiro
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import java.util.concurrent.TimeUnit


class Authentication : AppCompatActivity(){
      lateinit var mCallbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
      lateinit var mVerificationCode:String
      private  var mAuth = FirebaseAuth.getInstance()
      private  var mensageSystemApp = MensagesSystemApp()

      private val falhaNaVerificaacoDoTelefone = "Por favor digite novavemnte seu numero de telefone"
      private val contaCriadaComEmailESenhaSuccessfuly ="Conta criada com sucesso"
      private val contaCriadaEmailSenhaError ="Erro ao criar sua conta"
      private val avisoDeVerifcacaoParaEmail = "Foi enviado um email de verificação"
      private val loginComEmailSenhaSucesso ="Login OK"
      private val errorLoginEmailSenha ="Login invalido"

      private var contextApp = baseContext

    fun checkIfEmailVerified():Boolean{
        val currentUser = mAuth.currentUser
        Log.d(TAG, "Teste de saida: "+currentUser.isEmailVerified.toString())
        return currentUser.isEmailVerified
    }

    fun initilizedMCallbacks(): PhoneAuthProvider.OnVerificationStateChangedCallbacks {
        mCallbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
            override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
                super.onCodeSent(p0, p1)
                mVerificationCode = p0
            }
            override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                val code = p0.smsCode
                code?.let { verifyPhoneNumberWithCode(mVerificationCode,it) }
            }
            override fun onVerificationFailed(p0: FirebaseException) {
                mensageSystemApp.showMensage(falhaNaVerificaacoDoTelefone,contextApp)
            }
        }
        return this.mCallbacks
    }

    fun startPhoneNumberVerification(telefoneUser: String?, activity: Activity) {
        var mAuth = FirebaseAuth.getInstance()
        val options = PhoneAuthOptions.newBuilder(mAuth)
                .setPhoneNumber(telefoneUser) // Phone number to verify
                .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                .setCallbacks(initilizedMCallbacks()) // OnVerificationStateChangedCallbacks
                .setActivity(activity)
                .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    fun verifyPhoneNumberWithCode(verificationId: String?, code: String?) {
        // [START verify_with_code]
        val credential = PhoneAuthProvider.getCredential(verificationId, code)
        signInWithPhoneAuthCredential(credential)
    }
    fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential?){
        mAuth.signInWithCredential(credential).addOnCompleteListener{ task ->
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

    fun createAccountWithEmailAndPassword(passageiro :Passageiro){
        mAuth.createUserWithEmailAndPassword(passageiro.email, passageiro.senha)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful){
                        val userId = mAuth.currentUser
                        salvarInstanciaDoUsuarioNoBancoDeDados(userId.uid,passageiro)
                        sendEmailVerification()
                        mensageSystemApp.showMensage(contaCriadaComEmailESenhaSuccessfuly,contextApp)
                    }else{
                        mensageSystemApp.showMensage(contaCriadaEmailSenhaError,contextApp)
                       // Adicionar exception de falha
                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                    }
                }

    }

    fun salvarInstanciaDoUsuarioNoBancoDeDados(userId:String, passageiro: Passageiro){
        var passageiroControllersImp = PassageiroControllers()
        passageiroControllersImp.salvandoPassageiroNoBancoDeDados(userId,passageiro)
        //Adicionar exception de sucesso ao salva no banco de dados
    }

    fun sendEmailVerification() {
        val user = mAuth.currentUser!!
        user.sendEmailVerification()
            .addOnCompleteListener { task ->
                mensageSystemApp.showMensage(avisoDeVerifcacaoParaEmail,contextApp)
            }
    }

    fun signInEmailAndPassword(email: String, password: String) {
        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful){
                    mensageSystemApp.showMensage(loginComEmailSenhaSucesso,contextApp)
                }else{
                    mensageSystemApp.showMensage(errorLoginEmailSenha,contextApp)
                }
            }
        // [END sign_in_with_email]
    }

}


