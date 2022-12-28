package com.example.corridafacil.view.auth.ui.ui.theme.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
fun inputPassword(
    labelName: String,
    isValidateField: Boolean,
    errorMessage: String,
    value: String,
    onValueChange: (String)-> Unit
){

    var passwordVisibility by remember { mutableStateOf( false)    }

    val icon  = if (passwordVisibility)
        painterResource(id = R.drawable.visibility_password)
    else
        painterResource(id = R.drawable.visibility_password_off)

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(10.dp)
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = { onValueChange(it) },
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

        if(!isValidateField){
            Text(text = errorMessage,
                color = Color.Red,
                style = MaterialTheme.typography.overline,
                modifier = Modifier
                    .padding(start = 28.dp,
                        top = 16.dp)
                    .offset(y=(-8).dp)
                    .fillMaxWidth(0.9f)

            )
        }
    }

}
