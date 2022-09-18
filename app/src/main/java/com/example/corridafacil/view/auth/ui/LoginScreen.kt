package com.example.corridafacil.view.auth.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.corridafacil.R
import com.example.corridafacil.utils.responsive.WindowSize
import com.example.corridafacil.utils.responsive.rememberWindowSize
import com.example.corridafacil.view.auth.ui.ui.theme.*

class LoginScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CorridaFacilTheme {
                val window = rememberWindowSize()
                LoginPage(window)
            }
        }
    }
}

@Composable
fun fieldPasswordInput( labelName: String){
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
            label = {Text(labelName, color = MaterialTheme.colors.secondary)},
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
                focusedIndicatorColor = MaterialTheme.colors.secondary,
                unfocusedIndicatorColor = MaterialTheme.colors.secondary
            ))
    }
}

@Composable
fun fieldEmailInput( labelName: String){
    var text by remember { mutableStateOf("") }

    Box(modifier = Modifier.padding(10.dp)){
        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            textStyle = TextStyle(fontFamily = CustomFonts().getInterFamily()),
            label = {Text(labelName, color = MaterialTheme.colors.secondary)},
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = MaterialTheme.colors.secondary,
                unfocusedIndicatorColor = MaterialTheme.colors.secondary
            )

        )

    }
}

@Composable
fun buttonLogin(){
    
    Box(modifier = Modifier.padding(top = 26.dp)) {
        Button(onClick = { /*TODO*/ },
            colors= ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.surface),
            modifier = Modifier
                .height(40.dp)
                .width(100.dp)
        ) {
            Text("Sign in", style = MaterialTheme.typography.overline, color = Color.White, fontSize = 14.sp)
        }
    }

}


@Composable
fun LoginPage(window: WindowSize) {

    BoxWithConstraints(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)) {
        Background(window)
        Column( verticalArrangement = Arrangement.Center,
                modifier = Modifier
                            .fillMaxSize()
                            .padding(24.dp)
            ){
                Text(text = "Login",
                     style = MaterialTheme.typography.h1)
                Text(text = "Por favor, adicione suas credenciais para continuar:",
                    style = MaterialTheme.typography.overline)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
            ) {
                Column( verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(top = 50.dp)
                ){

                    fieldEmailInput(labelName = "Email")
                    fieldPasswordInput(labelName = "Senha")
                    buttonLogin()

                    Row(modifier = Modifier.padding(top = 45.dp)){
                        Text(text = "Não é cadastrado?",
                            style = MaterialTheme.typography.overline,
                            fontSize = 14.sp)
                        Text(text = " Faça seu cadastro",
                            style = MaterialTheme.typography.h3,
                            color = MaterialTheme.colors.onSurface)
                    }

                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    CorridaFacilTheme {
        val window = rememberWindowSize()
        LoginPage(window)
    }
}