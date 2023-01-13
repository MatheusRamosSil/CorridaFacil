package com.example.corridafacil.view.auth.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.corridafacil.utils.responsive.WindowSize
import com.example.corridafacil.utils.validators.ValidatorsFieldsForms.isValidEmail
import com.example.corridafacil.utils.validators.ValidatorsFieldsForms.isValidPassword
import com.example.corridafacil.utils.validators.errorMessageUI.MessageErrorForBadFormatInFormsFields
import com.example.corridafacil.view.auth.ui.ui.theme.Background
import com.example.corridafacil.view.auth.ui.ui.theme.components.*
import com.example.corridafacil.view.auth.viewModel.EmailViewModel
import com.example.corridafacil.view.utils.ScreensNavigate



@Composable
fun LoginScreen(window: WindowSize,
                navController: NavController,
                viewModelEmail: EmailViewModel
) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isFormatValidEmail by remember { mutableStateOf(true) }
    var isFormatValidPassword by remember { mutableStateOf(true) }

    fun validateDataInput(email:String,
                          password: String
    ): Boolean {
        isFormatValidEmail = isValidEmail(email)
        isFormatValidPassword = isValidPassword(password)

        return isFormatValidEmail && isFormatValidPassword
    }

    fun login(viewModelEmail: EmailViewModel){
        if (validateDataInput(email, password)){
            viewModelEmail.login(email,password)
        }
    }

    Background(window)
    Column( modifier= Modifier
        .fillMaxSize()
        .navigationBarsPadding()
        .imePadding()
        .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Title("Login",
            "Por favor, adicione suas credenciais para continuar:")


        inputOutlinedText(
            labelName = "Email",
            isValidateField = isFormatValidEmail,
            errorMessage = MessageErrorForBadFormatInFormsFields.EMAIL_FORMAT_BAD,
            value = email,
            onValueChange= {email = it},
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp),
            typeKeyBoard = KeyboardType.Text
        )
        inputPassword(
            value = password,
            labelName = "Senha",
            isValidateField = isFormatValidPassword,
            errorMessage = MessageErrorForBadFormatInFormsFields.PASSWORD_FORMAT_BAD,
            onValueChange = {password = it},
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
        )
        Button(40.dp,100.dp,
            { login(viewModelEmail)})
        MakerNewRegister(navController)


    }


}


@Composable
fun MakerNewRegister(navController: NavController) {

    Row(modifier = Modifier.padding(top = 45.dp)){
        Text(text = "Não é cadastrado?",
            style = MaterialTheme.typography.overline,
            fontSize = 14.sp)
        Text(text = " Faça seu cadastro",
            modifier = Modifier.clickable {
                navController.navigate(ScreensNavigate.Profile.route)
            },
            style = MaterialTheme.typography.h3,
            color = MaterialTheme.colors.onSurface)
    }
}

/*@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    CorridaFacilTheme {
        val window = rememberWindowSize()
        LoginScreen(window, rememberNavController())
    }
}

 */