package com.example.corridafacil.view.auth.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.corridafacil.R
import com.example.corridafacil.domain.services.AuthenticationFirebaseSevice.Email.AuthenticationEmailFirebaseServiceImpl
import com.example.corridafacil.domain.services.FirebaseCloudStorage.FirebaseStorageCloud
import com.example.corridafacil.data.repository.auth.email.EmailRepositoryImpl
import com.example.corridafacil.view.auth.viewModel.EmailViewModel
import com.example.corridafacil.view.auth.viewModel.Result
import com.example.corridafacil.view.auth.viewModel.factories.ViewModelEmailFactory
import com.example.corridafacil.databinding.ActivityForgoutBinding
import com.example.corridafacil.data.models.dao.PassageiroDAOImpl
import com.example.corridafacil.utils.validators.ValidatorsFieldsForms.isValidEmail
import com.example.corridafacil.utils.validators.errorMessageUI.MessageErrorForBadFormatInFormsFields
import com.example.corridafacil.utils.validators.errorMessageUI.ShowMessageErrorBadFormart.showMensageErrorFormaBad
import com.google.android.material.snackbar.Snackbar

class ForgoutPassword : AppCompatActivity() {
    private lateinit var binding: ActivityForgoutBinding
    private lateinit var viewModelEmail: EmailViewModel
    private lateinit var fieldEmail: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgout)
        binding = ActivityForgoutBinding.inflate(layoutInflater)
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
    }

    override fun onStart() {
        super.onStart()
        fieldEmail = findViewById(R.id.editTextTextEmailAddress)
        binding.button.setOnClickListener{enviarLink()}
    }

    override fun onResume() {
        super.onResume()

        lifecycleScope.launchWhenStarted {
            viewModelEmail.stateUI.collect {
                when(it){
                    is Result.Success ->{
                        Toast.makeText(this@ForgoutPassword,"Um link para redefinir sua senha foi enviado para o seu email",Toast.LENGTH_SHORT).show()
                    }
                    is Result.Error ->{
                        Snackbar.make(binding.root,it.exception.message.toString(), Snackbar.LENGTH_SHORT).show()
                    }
                    else -> Unit
                }
            }
        }

    }

    private fun enviarLink() {
        val formatEmailValid = isValidEmail(fieldEmail.text.toString())
        checkFormatEmailValid(formatEmailValid)
    }

    private fun checkFormatEmailValid(formatEmailValid: Boolean) {
        if (formatEmailValid){
            viewModelEmail.forgotPassword(binding.editTextTextEmailAddress.text.toString())
        }else{
            formatEmailValid.showMensageErrorFormaBad(binding.editTextTextEmailAddress, MessageErrorForBadFormatInFormsFields.EMAIL_FORMAT_BAD)
        }
    }
}