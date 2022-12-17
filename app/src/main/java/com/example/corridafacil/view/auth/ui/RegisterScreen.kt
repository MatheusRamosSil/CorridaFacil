package com.example.corridafacil.view.auth.ui

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.corridafacil.R
import com.example.corridafacil.utils.responsive.WindowSize
import com.example.corridafacil.utils.responsive.rememberWindowSize
import com.example.corridafacil.view.auth.ui.ui.theme.*

@Composable
fun RegisterScreen(window: WindowSize, navController: NavHostController) {
    BoxWithConstraints(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)){
        Background(window)

        Column( verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ){
            Text(text = "Cadastro",
                style = MaterialTheme.typography.h1)
            Text(text = "Por favor, adicione seus dados a seguir:",
                style = MaterialTheme.typography.overline)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
            ){
                Column( verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(top = 50.dp)
                        .imePadding()
                ){

                    fieldProfile()
                    fieldInput(labelName = "Nome")
                    fieldInput(labelName = "Sobrenome")
                    fieldInput(labelName = "Email")
                    fieldPassword(labelName = "Senha")

                    buttonNextPage()
                }
            }
        }

    }
}

@Composable
fun buttonNextPage() {

    Box(modifier = Modifier
        .padding(26.dp)
        .height(64.dp)
        .width(287.dp)
        .clip(RoundedCornerShape(10.dp))
        .background(YellowGradientCircle)) {
        Row(verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)) {
            Text("Proximo",
                    style = MaterialTheme.typography.h3,
                    color = Color.Black,
                    fontSize = 14.sp)
                Icon(painter = painterResource(id = R.drawable.arrow), contentDescription = "Arrow")
        }
    }


}

@Composable
fun fieldProfile() {
        Row(horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 12.dp)){
            Column(verticalArrangement = Arrangement.Center,
                   horizontalAlignment = Alignment.CenterHorizontally) {
                BoxWithConstraints(
                    modifier = Modifier.padding(25.dp)
                ) {
                    Canvas(modifier = Modifier.padding(start = 48.dp, top = 48.dp)){
                        drawCircle(
                            color = Color.Black,
                            radius= 260f,
                            style = Stroke( 3.dp.toPx())
                        )
                    }

                    Image(painter = painterResource(id = R.drawable.profile),
                        contentDescription = null,
                        modifier = Modifier.width(100.dp)
                    )
                }

            }



        }
}

@Composable
fun fieldInput( labelName: String){
    var text by remember { mutableStateOf("") }

    Box(modifier = Modifier
        .padding(8.dp)
        .verticalScroll(rememberScrollState(), reverseScrolling = true)){
        TextField(
            value = text,
            onValueChange = { text = it },
            textStyle = TextStyle(fontFamily = CustomFonts().getInterFamily()),
            label = {Text(labelName, style = MaterialTheme.typography.subtitle1)},
            maxLines = 1,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.White,
                focusedIndicatorColor = Amber,
                unfocusedIndicatorColor = Amber
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),

        )

    }
}

@Composable
fun fieldPassword( labelName: String){
    var password by rememberSaveable { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf( false)    }

    val icon  = if (passwordVisibility)
        painterResource(id = R.drawable.visibility_password)
    else
        painterResource(id = R.drawable.visibility_password_off)

    Box(modifier = Modifier
        .padding(10.dp)
        .verticalScroll(rememberScrollState(), reverseScrolling = true)){
        TextField(
            value = password,
            onValueChange = { password = it },
            textStyle = TextStyle(fontFamily = CustomFonts().getInterFamily()),
            maxLines = 1,
            label = {Text(labelName,
                          style = MaterialTheme.typography.subtitle1)},
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
                backgroundColor = Color.White,
                focusedIndicatorColor = Amber,
                unfocusedIndicatorColor = Amber
            ))
    }
}

@Preview(showBackground = true)
@Composable
fun RegistertPreview() {
    CorridaFacilTheme {
        val window = rememberWindowSize()
        RegisterScreen(window, rememberNavController())
    }
}