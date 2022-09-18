package com.example.corridafacil.view.auth.ui.register

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import com.example.corridafacil.R
import com.example.corridafacil.utils.responsive.rememberWindowSize
import com.example.corridafacil.view.auth.ui.RegisterScreen
import com.example.corridafacil.view.auth.ui.register.ui.theme.CorridaFacilTheme

class FormProfile : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        setContent {
            CorridaFacilTheme {
             val window = rememberWindowSize()
             RegisterScreen(window)
            }
        }
    }
}
