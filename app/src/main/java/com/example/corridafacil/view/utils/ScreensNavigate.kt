package com.example.corridafacil.view.utils

sealed class ScreensNavigate(val route:String){
    object Login : ScreensNavigate( route = "login_screen")
    object Profile: ScreensNavigate(route ="profile_screen")
    object RegisterEmail : ScreensNavigate(route="register_email")
    object RegisterPhone : ScreensNavigate(route = "register_phone")
}
