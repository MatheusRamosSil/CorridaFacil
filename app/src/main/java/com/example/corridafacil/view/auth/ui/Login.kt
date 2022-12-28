package com.example.corridafacil.view.auth.ui

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.corridafacil.R
import com.example.corridafacil.view.auth.ui.componentsView.ComponentsViewActivity
import com.example.corridafacil.view.auth.ui.register.FormAddPhone
import com.example.corridafacil.view.auth.viewModel.EmailViewModel
import com.example.corridafacil.view.auth.viewModel.Result
import com.example.corridafacil.databinding.ActivityLoginBinding
import com.example.corridafacil.utils.permissions.Permissions.acessFineLocationPermissions
import com.example.corridafacil.view.mapa.ui.MapsActivity
import com.example.corridafacil.utils.validators.ValidatorsFieldsForms.isValidEmail
import com.example.corridafacil.utils.validators.ValidatorsFieldsForms.isValidPassword
import com.example.corridafacil.utils.validators.errorMessageUI.MessageErrorForBadFormatInFormsFields
import com.example.corridafacil.utils.validators.errorMessageUI.ShowMessageErrorBadFormart.showMensageErrorFormaBad
import com.example.corridafacil.utils.permissions.Permissions.checkForPermissions
import com.example.corridafacil.utils.permissions.Permissions.hasReadExternalStoragePermission
import com.example.corridafacil.utils.permissions.Permissions.isLocationEnabled
import com.example.corridafacil.utils.responsive.rememberWindowSize
import com.example.corridafacil.view.auth.ui.ui.theme.CorridaFacilTheme
import com.example.corridafacil.view.utils.SetupNavigation
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class Login : AppCompatActivity() {

    private  lateinit var binding: ActivityLoginBinding
    private  val viewModelEmail: EmailViewModel by viewModels()
    private lateinit var componentsViewLogin: ComponentsViewActivity
    private lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CorridaFacilTheme {
                val window = rememberWindowSize()
                navController = rememberNavController()
                SetupNavigation(navController,window)
            }
        }


        checkForPermissions(this,android.Manifest.permission.ACCESS_FINE_LOCATION,"Permission location",1)

        checkGPSEnabled()
        val result = isLocationEnabled()
        Log.w("Location enabled", "result : ${result}")

    }



    private fun checkGPSEnabled() {
       if (!isLocationEnabled()){
           showAlert()
       }
    }

    private fun showAlert() {
        val dialog = AlertDialog.Builder(this)
        dialog.setTitle("Ative sua localização")
            .setMessage("A localização do seu dispositivo deve estar 'desligada'.\nPor favor ative sua localização")
            .setPositiveButton("Abrir configurações") { paramDialogInterface, paramInt ->
                val myIntent = Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(myIntent)
            }
            .setNegativeButton("Cancel") { paramDialogInterface, paramInt -> }
        dialog.show()
    }

    override fun onStart() {
        super.onStart()

       // viewModelEmail.checkDeviceAndEmailOfLoggedUser()
        //binding.botaoRegister.setOnClickListener{register()}
        //binding.botaoLogin.setOnClickListener{login()}
        //binding.botaoForgotPassword.setOnClickListener{forgotPassword()}


    }

    override fun onResume() {
        super.onResume()

      lifecycleScope.launchWhenStarted {
          Log.w("Life cycle", " entrou no ciclo de vida do app")
          viewModelEmail.stateUI.collect {
              Log.w("Value da opcao", "${it}")
              when(it){
                  is Result.Success ->{
                      val emailIsVerified = viewModelEmail.checkUserAuthenticated()!!.isEmailVerified
                      Log.w("Email is verified", "${emailIsVerified}")
                     // if (emailIsVerified){
                      //    startActivity(Intent(this@Login, MapsActivity::class.java))
                      //}else{
                       //   Toast.makeText(this@Login, "Por favor verifique o email de verificação que foi enviado para o seu email",Toast.LENGTH_SHORT).show()
                      //}
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