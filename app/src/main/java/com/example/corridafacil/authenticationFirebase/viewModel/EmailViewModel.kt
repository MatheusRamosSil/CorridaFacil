package com.example.corridafacil.authenticationFirebase.viewModel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.corridafacil.Models.Passageiro
import com.example.corridafacil.Services.AuthenticationFirebaseSevice.Models.AuthEmail
import com.example.corridafacil.Services.AuthenticationFirebaseSevice.AuthFirebaseEmailCallbackImp
import com.example.corridafacil.authenticationFirebase.Repository.EmailRepository
import com.example.corridafacil.authenticationFirebase.data.FirebaseStorageCallbackImp
import java.lang.Exception

class EmailViewModel(private val emailViewModelRepository: EmailRepository): ViewModel() {

    var passageiro = Passageiro.create()
    lateinit var imagemEscolhida:Uri
    var uidUser = "path uid"
    var urlImage = "path url"
    var authEmail = AuthEmail.create()
    var valorDeStatusLogin = MutableLiveData<String>()
    var errorStatusLogin = MutableLiveData<String>()

    fun register(){
        authEmail.email = passageiro.email.toString()
        authEmail.password = passageiro.senha.toString()

        emailViewModelRepository.register(object : AuthFirebaseEmailCallbackImp{
            override fun getUidUser(uid: String) {
                uidUser = uid
                savePassengerInDataBase()
                Log.i("Id return", "uid my user: "+uid)
            }

            override fun onSuccess() {
                TODO("Not yet implemented")
            }

            override fun onFailure(menssage: Exception?) {
                TODO("Not yet implemented")
            }

        },authEmail)

    }

    fun login(){
        emailViewModelRepository.loginEmail(object : AuthFirebaseEmailCallbackImp{
            override fun getUidUser(uid: String) {
                valorDeStatusLogin.postValue(uid)
            }

            override fun onSuccess() {

            }

            override fun onFailure(menssage: Exception?) {
                errorStatusLogin.postValue(menssage?.message.toString())
            }

        },authEmail)
    }

    fun fazerUploadDaImagemEPegarAUrl() {
        emailViewModelRepository.fazerUploadDaImagemEPegarAUrl(object :FirebaseStorageCallbackImp{
            override fun pegarAUrlAposUplaod(urlDaImagem: String) {
                urlImage = urlDaImagem
                Log.i("Return url", "my url "+ urlDaImagem)
            }

        },imagemEscolhida)
    }

    fun savePassengerInDataBase(){
        Log.i("Teste values", " my uid :" + uidUser+" my url :"+urlImage)
        passageiro.id = uidUser
        emailViewModelRepository.salvandoUmNovoPassageiroNoBancoDeDados(passageiro)
    }

}


