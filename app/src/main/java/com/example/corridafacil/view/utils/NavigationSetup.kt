package com.example.corridafacil.view.utils

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.corridafacil.utils.responsive.WindowSize
import com.example.corridafacil.view.auth.ui.LoginScreen
import com.example.corridafacil.view.auth.ui.register.CodeOTPScreen
import com.example.corridafacil.view.auth.ui.register.EmailRegister
import com.example.corridafacil.view.auth.ui.register.PhoneRegister
import com.example.corridafacil.view.auth.ui.register.ProfileRegisterScreen
import com.example.corridafacil.view.auth.viewModel.EmailViewModel

@Composable
fun SetupNavigation(
    navController: NavHostController,
    window: WindowSize,
    viewModelEmail: EmailViewModel = hiltViewModel()
){
    NavHost(navController = navController,
            startDestination = ScreensNavigate.Login.route){
        composable(
            route = ScreensNavigate.Login.route
        ){
            LoginScreen(window = window, navController,viewModelEmail)
        }
        
        composable(
            route = ScreensNavigate.Profile.route
        ){
            ProfileRegisterScreen(window = window, navController,viewModelEmail)
        }

        composable(
            route = ScreensNavigate.RegisterEmail.route
        ){
            EmailRegister(window, navController, viewModelEmail)
        }

        composable(
            route = ScreensNavigate.RegisterPhone.route
        ){
            PhoneRegister(window = window, navController = navController )
        }

       /* composable(
            route = ScreensNavigate.RegisterCodeOTP.route
        ){
            CodeOTPScreen(window = window,
                            navController = navController)
        }

        */

    }
}