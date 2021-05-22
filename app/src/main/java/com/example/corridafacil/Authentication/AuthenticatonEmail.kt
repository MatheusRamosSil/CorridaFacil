package com.example.corridafacil.Authentication

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.corridafacil.Controller.PassageiroControllers
import com.example.corridafacil.Models.Passageiro
import com.google.firebase.auth.FirebaseAuth

class AuthenticatonEmail : AppCompatActivity() {
    private  var mAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    fun checkIfEmailVerified():Boolean{
        val currentUser = mAuth.currentUser
        Log.d(ContentValues.TAG, "Teste de saida: "+currentUser.isEmailVerified.toString())
        return currentUser.isEmailVerified
    }

    fun createAccountWithEmailAndPassword(passageiro : Passageiro){
        mAuth.createUserWithEmailAndPassword(passageiro.email, passageiro.senha)
            .addOnCompleteListener { task ->
                if (task.isSuccessful){
                    val userId = mAuth.currentUser
                    salvarInstanciaDoUsuarioNoBancoDeDados(userId.uid,passageiro)
                    sendEmailVerification()

                }else{
                    // Adicionar exception de falha
                    Log.w(ContentValues.TAG, "createUserWithEmail:failure", task.getException());
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

            }
    }

    fun signInEmailAndPassword(email: String, password: String) {
        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful){

                }else{

                }
            }
        // [END sign_in_with_email]
    }
}