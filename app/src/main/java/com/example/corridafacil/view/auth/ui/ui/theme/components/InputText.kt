package com.example.corridafacil.view.auth.ui.ui.theme.components

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import com.example.corridafacil.view.auth.ui.ui.theme.Amber
import com.example.corridafacil.view.auth.ui.ui.theme.CustomFonts
import com.example.corridafacil.view.auth.ui.ui.theme.White

@Composable
fun inputText(

    labelName: String,
    isValidateField: Boolean,
    errorMessage: String,
    value: String,
    onValueChange: (String)-> Unit
){

    Column(verticalArrangement = Arrangement.Center) {
        OutlinedTextField(
            value = value,
            onValueChange = {onValueChange(it)},
            textStyle = TextStyle(fontFamily = CustomFonts().getInterFamily()),
            label = { Text(labelName, color = Amber) },
            maxLines = 1,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = White,
                focusedIndicatorColor = Amber,
                unfocusedIndicatorColor = Amber
            )

        )

        if(!isValidateField){
            Text(text = errorMessage,
                color = Color.Red,
                style = MaterialTheme.typography.overline
            )
        }
    }




}