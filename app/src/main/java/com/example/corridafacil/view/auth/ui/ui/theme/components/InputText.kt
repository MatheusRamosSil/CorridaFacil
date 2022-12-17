package com.example.corridafacil.view.auth.ui.ui.theme.components

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.text.TextStyle
import com.example.corridafacil.view.auth.ui.ui.theme.Amber
import com.example.corridafacil.view.auth.ui.ui.theme.CustomFonts
import com.example.corridafacil.view.auth.ui.ui.theme.White

@Composable
fun InputText( labelName: String): String {
    var text by remember { mutableStateOf("") }

    Box(){
        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            textStyle = TextStyle(fontFamily = CustomFonts().getInterFamily()),
            label = { Text(labelName, color = Amber) },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = White,
                focusedIndicatorColor = Amber,
                unfocusedIndicatorColor = Amber
            )

        )

    }

    return text
}