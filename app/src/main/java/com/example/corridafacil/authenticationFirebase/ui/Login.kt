package com.example.corridafacil.authenticationFirebase.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.corridafacil.R
import com.example.corridafacil.Services.AuthenticationFirebaseSevice.Email.AuthenticationEmailFirebaseServiceImpl
import com.example.corridafacil.Services.FirebaseCloudStorage.FirebaseStorageCloud
import com.example.corridafacil.authenticationFirebase.repository.email.EmailRepositoryImpl
import com.example.corridafacil.authenticationFirebase.ui.componentsView.ComponentsViewActivity
import com.example.corridafacil.authenticationFirebase.ui.register.FormAddPhone
import com.example.corridafacil.authenticationFirebase.viewModel.EmailViewModel
import com.example.corridafacil.authenticationFirebase.viewModel.Result
import com.example.corridafacil.authenticationFirebase.viewModel.factories.ViewModelEmailFactory
import com.example.corridafacil.databinding.ActivityLoginBinding
import com.example.corridafacil.mapa.ui.MapsActivity
import com.example.corridafacil.models.dao.PassageiroDAOImpl
import com.example.corridafacil.utils.ValidatorsFieldsForms.isValidEmail
import com.example.corridafacil.utils.ValidatorsFieldsForms.isValidPassword
import com.example.corridafacil.utils.errorMessageUI.MessageErrorForBadFormatInFormsFields
import com.example.corridafacil.utils.errorMessageUI.ShowMessageErrorBadFormart.showMensageErrorFormaBad
import kotlinx.coroutines.flow.collect

class Login : AppCompatActivity() {

    private  lateinit var binding: ActivityLoginBinding
    private lateinit var viewModelEmail: EmailViewModel
    private lateinit var componentsViewLogin: ComponentsViewActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModelEmail =
            ViewModelProvider(this, ViewModelEmailFactory(
                EmailRepositoryImpl(
                    PassageiroDAOImpl(),
                    AuthenticationEmailFirebaseServiceImpl(),
                    FirebaseStorageCloud()
                )
            )
            )
                .get(EmailViewModel::class.java)
        binding.viewmodel = viewModelEmail

        componentsViewLogin = ComponentsViewActivity(this)
    }

    override fun onStart() {
        super.onStart()

        viewModelEmail.checkEmailIsVerifiead()
        binding.botaoRegister.setOnClickListener{register()}
        binding.botaoLogin.setOnClickListener{login()}
        binding.botaoForgotPassword.setOnClickListener{forgotPassword()}


    }

    override fun onResume() {
        super.onResume()

      lifecycleScope.launchWhenStarted {
          viewModelEmail.stateUI.collect {
              when(it){
                  is Result.Success ->{
                      val emailIsVerified = viewModelEmail.checkUserAuthenticated()!!.isEmailVerified
                      if (emailIsVerified){
                          startActivity(Intent(this@Login, MapsActivity::class.java))
                      }else{
                          Toast.makeText(this@Login, "Por favor verifique o email de verificação que foi enviado para o seu email",Toast.LENGTH_SHORT).show()
                      }
                  }
                  is Result.Error ->{
                      Toast.makeText(this@Login, it.exception.message.toString(), Toast.LENGTH_SHORT).show()
                  }
                  else -> Unit
              }
          }
      }
    }

    private fun forgotPassword() {
        val intent = Intent(this, ForgoutPassword::class.java)
        startActivity(intent)
    }

    fun register() {
        val intent = Intent(this, FormAddPhone::class.java)
        startActivity(intent)
    }

    fun login(){

        val formatEmail = isValidEmail(componentsViewLogin.getValueFieldEdtiTextToString(R.id.editTextLoginEmailAddress))
        val formatPassword = isValidPassword(componentsViewLogin.getValueFieldEdtiTextToString(R.id.editTextLoginPassword))

        if (formatEmail && formatPassword){
            viewModelEmail.login(componentsViewLogin.getValueFieldEdtiTextToString(R.id.editTextLoginEmailAddress),
                componentsViewLogin.getValueFieldEdtiTextToString(R.id.editTextLoginPassword))
        }else{
            formatEmail.showMensageErrorFormaBad(componentsViewLogin.getComponentEditText(R.id.editTextLoginEmailAddress),
                MessageErrorForBadFormatInFormsFields.EMAIL_FORMAT_BAD)
            formatPassword.showMensageErrorFormaBad(componentsViewLogin.getComponentEditText(R.id.editTextLoginPassword),MessageErrorForBadFormatInFormsFields.PASSWORD_FORMAT_BAD)
        }

    }
}