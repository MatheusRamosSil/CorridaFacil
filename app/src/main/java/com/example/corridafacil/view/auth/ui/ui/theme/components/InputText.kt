package com.example.corridafacil.view.auth.ui.ui.theme.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.corridafacil.view.auth.ui.ui.theme.Amber
import com.example.corridafacil.view.auth.ui.ui.theme.CustomFonts
import com.example.corridafacil.view.auth.ui.ui.theme.White
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@Composable
fun inputOutlinedText(

    labelName: String,
    isValidateField: Boolean,
    errorMessage: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier.padding(0.dp),
    typeKeyBoard: KeyboardType,

    ){

    Column( modifier = modifier,
            verticalArrangement = Arrangement.Center) {
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
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = typeKeyBoard)
        )

        if(!isValidateField){
            Text(text = errorMessage,
                color = Color.Red,
                style = MaterialTheme.typography.overline
            )
        }
    }

}

@Composable
fun inputOutlinedPhone(
    labelName: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier.padding(0.dp),
    ){

    Column( modifier = modifier,
        verticalArrangement = Arrangement.Center) {
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
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Phone)
        )
    }

}

@OptIn(ExperimentalFoundationApi::class,
       ExperimentalComposeUiApi::class)
@Composable
fun fieldInputText(
    labelName: String,
    isValidateField: Boolean,
    errorMessage: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier.padding(0.dp),
    bringIntoViewRequester: BringIntoViewRequester = remember { BringIntoViewRequester()   }
){

    val coroutineScope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center){
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
            maxLines = 1,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = White,
                focusedIndicatorColor = Amber,
                unfocusedIndicatorColor = Amber
            ),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() })
        )

        if(!isValidateField){
            Text(text = errorMessage,
                color = Color.Red,
                style = MaterialTheme.typography.overline
            )
        }
    }
}