package com.example.corridafacil.view.auth.ui.ui.theme.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.corridafacil.R
import com.example.corridafacil.view.auth.ui.ui.theme.Amber
import com.example.corridafacil.view.auth.ui.ui.theme.CustomFonts
import com.example.corridafacil.view.auth.ui.ui.theme.White

@Composable
fun InputPassword( labelName: String): String {
    var password by rememberSaveable { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf( false)    }

    val icon  = if (passwordVisibility)
        painterResource(id = R.drawable.visibility_password)
    else
        painterResource(id = R.drawable.visibility_password_off)

    Box(modifier = Modifier
        .padding(10.dp)){
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            textStyle = TextStyle(fontFamily = CustomFonts().getInterFamily()),
            label = { Text(labelName, color = Amber) },
            trailingIcon = {
                IconButton(onClick = {
                    passwordVisibility = !passwordVisibility
                }) {
                    Icon(painter = icon,
                        contentDescription ="Visibility Icon" )
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = if (passwordVisibility) VisualTransformation.None
            else PasswordVisualTransformation(),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = White,
                focusedIndicatorColor = Amber,
                unfocusedIndicatorColor = Amber

            ))
    }

    return password
}
