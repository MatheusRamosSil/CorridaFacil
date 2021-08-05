package com.example.corridafacil.authenticationFirebase.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.corridafacil.R
import com.example.corridafacil.Services.AuthenticationFirebaseSevice.AuthenticationEmailFirebase
import com.example.corridafacil.authenticationFirebase.Repository.EmailRepository
import com.example.corridafacil.authenticationFirebase.Utils.irParaPaginaDoMapa
import com.example.corridafacil.authenticationFirebase.ui.Register.PrimeiroFormulario
import com.example.corridafacil.authenticationFirebase.viewModel.EmailViewModel
import com.example.corridafacil.authenticationFirebase.viewModel.EmailViewModelFactory
import com.example.corridafacil.databinding.ActivityLogin3Binding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLogin3Binding
    private  lateinit var emailViewModel: EmailViewModel
    var authenticationEmailFirebase = AuthenticationEmailFirebase.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login3)
        binding = ActivityLogin3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        emailViewModel = ViewModelProvider(this, EmailViewModelFactory(
            EmailRepository(authenticationEmailFirebase)
        )).get(EmailViewModel::class.java)
        binding.viewmodel = emailViewModel
    }

    override fun onStart() {
        super.onStart()

        val statusAuthenticated = authenticationEmailFirebase.currentUser()
        if (statusAuthenticated != null){
            irParaPaginaDoMapa()
        }

        binding.botaoLogin.setOnClickListener(this::loginPage)
        binding.botaoRegister.setOnClickListener(this::register)
    }

    fun register(view: View){
        startActivity(Intent(this,PrimeiroFormulario::class.java))
    }

    fun loginPage(view:View){
        adicionarValoresAosCamposLogin()
        binding.viewmodel?.login()
        binding.viewmodel?.valorDeStatusLogin?.observe(this, Observer {
           irParaPaginaDoMapa()
        })
        binding.viewmodel?.errorStatusLogin?.observe(this, Observer {
            Toast.makeText(this,it,Toast.LENGTH_SHORT).show()
        })
    }

    fun adicionarValoresAosCamposLogin(){
        binding.viewmodel?.authEmail?.email = binding.editTextTextEmailAddress.text.toString()
        binding.viewmodel?.authEmail?.password = binding.editTextTextPassword.text.toString()
    }
}