package com.example.corridafacil.view.auth.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.corridafacil.R
import com.example.corridafacil.domain.services.AuthenticationFirebaseSevice.Email.AuthenticationEmailFirebaseServiceImpl
import com.example.corridafacil.domain.services.FirebaseCloudStorage.FirebaseStorageCloud
import com.example.corridafacil.data.repository.auth.email.EmailRepositoryImpl
import com.example.corridafacil.view.auth.ui.componentsView.ComponentsViewActivity
import com.example.corridafacil.view.auth.ui.register.FormAddPhone
import com.example.corridafacil.view.auth.viewModel.EmailViewModel
import com.example.corridafacil.view.auth.viewModel.Result
import com.example.corridafacil.view.auth.viewModel.factories.ViewModelEmailFactory
import com.example.corridafacil.databinding.ActivityLoginBinding
import com.example.corridafacil.view.mapa.ui.MapsActivity
import com.example.corridafacil.data.models.dao.PassageiroDAOImpl
import com.example.corridafacil.utils.validators.ValidatorsFieldsForms.isValidEmail
import com.example.corridafacil.utils.validators.ValidatorsFieldsForms.isValidPassword
import com.example.corridafacil.utils.validators.errorMessageUI.MessageErrorForBadFormatInFormsFields
import com.example.corridafacil.utils.validators.errorMessageUI.ShowMessageErrorBadFormart.showMensageErrorFormaBad
import com.example.corridafacil.utils.permissions.Permissions.checkForPermissions
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class Login : AppCompatActivity() {

    private  lateinit var binding: ActivityLoginBinding
    private  val viewModelEmail: EmailViewModel by viewModels()
    private lateinit var componentsViewLogin: ComponentsViewActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.viewmodel = viewModelEmail
        componentsViewLogin = ComponentsViewActivity(this)

        checkForPermissions(this,android.Manifest.permission.ACCESS_FINE_LOCATION,"Permission location",1)

    }

    override fun onStart() {
        super.onStart()

        viewModelEmail.checkDeviceAndEmailOfLoggedUser()
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