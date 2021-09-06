package com.example.corridafacil.authenticationFirebase.Repository

import android.net.Uri
import com.example.corridafacil.dao.PassageiroDAO
import com.example.corridafacil.Models.Passageiro
import com.example.corridafacil.Services.AuthenticationFirebaseSevice.Models.AuthEmail
import com.example.corridafacil.Services.AuthenticationFirebaseSevice.AuthFirebaseEmailCallbackImp
import com.example.corridafacil.Services.AuthenticationFirebaseSevice.AuthenticationEmailFirebase
import com.example.corridafacil.authenticationFirebase.data.FirebaseStorageCallbackImp
import com.example.corridafacil.authenticationFirebase.data.FirebaseStorageCloud

class EmailRepository(private val authenticationEmailFirebase: AuthenticationEmailFirebase) {

    var uplaodImagePerfil = FirebaseStorageCloud()
    var passageiroDAOImp = PassageiroDAO()


    fun register(callbackImp: AuthFirebaseEmailCallbackImp, authEmail: AuthEmail) {
        authenticationEmailFirebase.register(callbackImp, authEmail)
    }

    fun fazerUploadDaImagemEPegarAUrl(callbackImp: FirebaseStorageCallbackImp,selectedUri:Uri){
        uplaodImagePerfil.fazerUploadDaImagemEPegarAUrl(callbackImp,selectedUri)

    }

    fun salvandoUmNovoPassageiroNoBancoDeDados(passageiro: Passageiro){
        passageiroDAOImp.salvandoPassageiroNoBancoDeDados(passageiro)
    }

    fun loginEmail(callbackImp: AuthFirebaseEmailCallbackImp,authEmail: AuthEmail){
        authenticationEmailFirebase.loginEmail(callbackImp,authEmail)
    }

    fun currentUser(){
        authenticationEmailFirebase.currentUser()
    }






}