package com.example.corridafacil.view.auth.ui.register

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.corridafacil.utils.responsive.rememberWindowSize
import com.example.corridafacil.view.auth.ui.ui.theme.CorridaFacilTheme
import com.example.corridafacil.view.auth.viewModel.PhoneViewModel
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class RegisteCodeOTPActivity : ComponentActivity() {


    private  val viewModel: PhoneViewModel by viewModels()


    lateinit var authPhone :AuthenticationPhone
    private lateinit var navController: NavHostController
    private  lateinit var codeSms: String
    lateinit var idCodeSent: String



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
          CorridaFacilTheme{
                val window = rememberWindowSize()
                navController = rememberNavController()
              CodeOTPScreen(
                  window=window,
                  navController=navController,
                  viewModel=viewModel,
              )
            }
        }

        val numberTest ="+5511123456789"
        val numberReal = "+5587999690335"
        viewModel.verifyPhoneNumber(numberTest,this)

    }


}

