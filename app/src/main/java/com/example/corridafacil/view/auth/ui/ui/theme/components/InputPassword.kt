package com.example.corridafacil.view.auth.ui.ui.theme.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.corridafacil.R
import com.example.corridafacil.view.auth.ui.ui.theme.Amber
import com.example.corridafacil.view.auth.ui.ui.theme.CustomFonts
import com.example.corridafacil.view.auth.ui.ui.theme.White
import kotlinx.coroutines.launch

@Composable
fun inputPassword(
    labelName: String,
    isValidateField: Boolean,
    errorMessage: String,
    value: String,
    onValueChange: (String)-> Unit,
    modifier: Modifier = Modifier.padding(0.dp),
){

    var passwordVisibility by remember { mutableStateOf( false)    }

    val icon  = if (passwordVisibility)
        painterResource(id = R.drawable.visibility_password)
    else
        painterResource(id = R.drawable.visibility_password_off)

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        OutlinedTextField(
            value = value,
            maxLines = 1,
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
                style = MaterialTheme.typography.overline
            )
        }
    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun fieldInputTextPassword(
    labelName: String,
    isValidateField: Boolean,
    errorMessage: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier.padding(0.dp),
    bringIntoViewRequester: BringIntoViewRequester = remember { BringIntoViewRequester()   }
){
    var passwordVisibility by remember { mutableStateOf( false)    }

    val icon  = if (passwordVisibility)
        painterResource(id = R.drawable.visibility_password)
    else
        painterResource(id = R.drawable.visibility_password_off)

    val coroutineScope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,){
        TextField(
            modifier = Modifier
                .onFocusEvent { focusState ->
                    if(focusState.isFocused){
                        coroutineScope.launch {
                            bringIntoViewRequester.bringIntoView()
                        }
                    }
                },
            value = value,
            onValueChange = {onValueChange(it) },
            textStyle = TextStyle(fontFamily = CustomFonts().getInterFamily()),
            label = {Text(labelName,style = MaterialTheme.typography.subtitle1)},
            trailingIcon = {
                IconButton(onClick = {
                    passwordVisibility = !passwordVisibility
                }) {
                    Icon(painter = icon,
                        contentDescription ="Visibility Icon" )
                }
            },
            maxLines = 1,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = White,
                focusedIndicatorColor = Amber,
                unfocusedIndicatorColor = Amber
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = if (passwordVisibility) VisualTransformation.None
            else PasswordVisualTransformation(),
        )

        if(!isValidateField){
            Column(
                modifier= Modifier.padding(12.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,)
            {
                Text(text = errorMessage,
                    color = Color.Red,
                    style = MaterialTheme.typography.overline
                )
            }

        }
    }
}
