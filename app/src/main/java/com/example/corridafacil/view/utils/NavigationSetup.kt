package com.example.corridafacil.view.utils

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.corridafacil.utils.responsive.WindowSize
import com.example.corridafacil.view.auth.ui.LoginScreen
import com.example.corridafacil.view.auth.ui.RegisterScreen
import com.example.corridafacil.view.auth.viewModel.EmailViewModel

@Composable
fun SetupNavigation(
    navController: NavHostController,
    window: WindowSize,
){
    NavHost(navController = navController,
            startDestination = ScreensNavigate.Login.route){
        composable(
            route = ScreensNavigate.Login.route
        ){
            LoginScreen(window = window, navController)
        }
        
        composable(
            route = ScreensNavigate.Profile.route
        ){
            RegisterScreen(window = window, navController)
        }
    }
}