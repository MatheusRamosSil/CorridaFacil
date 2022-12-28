package com.example.corridafacil.view.auth.ui

import android.util.Log
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.corridafacil.R
import com.example.corridafacil.utils.responsive.WindowSize
import com.example.corridafacil.utils.responsive.rememberWindowSize
import com.example.corridafacil.utils.validators.ValidatorsFieldsForms
import com.example.corridafacil.utils.validators.ValidatorsFieldsForms.isValidEmail
import com.example.corridafacil.utils.validators.ValidatorsFieldsForms.isValidPassword
import com.example.corridafacil.utils.validators.errorMessageUI.MessageErrorForBadFormatInFormsFields
import com.example.corridafacil.utils.validators.errorMessageUI.ShowMessageErrorBadFormart.showMensageErrorFormaBad
import com.example.corridafacil.view.auth.ui.ui.theme.Background
import com.example.corridafacil.view.auth.ui.ui.theme.CorridaFacilTheme
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
    Column( modifier= Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Title("Login",
            "Por favor, adicione suas credenciais para continuar:")


        inputText(
            value = email,
            labelName = "Email",
            isValidateField = isFormatValidEmail,
            errorMessage = MessageErrorForBadFormatInFormsFields.EMAIL_FORMAT_BAD,
            onValueChange= {email = it}
        )
        inputPassword(
            value = password,
            labelName = "Senha",
            isValidateField = isFormatValidPassword,
            errorMessage = MessageErrorForBadFormatInFormsFields.PASSWORD_FORMAT_BAD,
            onValueChange = {password = it}
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