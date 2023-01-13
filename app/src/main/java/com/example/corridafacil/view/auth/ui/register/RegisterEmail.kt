package com.example.corridafacil.view.auth.ui.register

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.corridafacil.utils.responsive.WindowSize
import com.example.corridafacil.utils.validators.ValidatorsFieldsForms
import com.example.corridafacil.utils.validators.errorMessageUI.MessageErrorForBadFormatInFormsFields

import com.example.corridafacil.view.auth.ui.ui.theme.Background
import com.example.corridafacil.view.auth.ui.ui.theme.components.Title
import com.example.corridafacil.view.auth.ui.ui.theme.components.fieldInputText
import com.example.corridafacil.view.auth.ui.ui.theme.components.fieldInputTextPassword
import com.example.corridafacil.view.auth.viewModel.EmailViewModel
import com.example.corridafacil.view.utils.ScreensNavigate
import com.togitech.ccp.component.*

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun EmailRegister(window: WindowSize,
                  navController: NavHostController,
                  viewModelEmail: EmailViewModel
) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }


    var isFormatValidEmail by remember { mutableStateOf(true) }
    var isFormatValidPassword by remember { mutableStateOf(true) }

    val bringIntoViewRequester = remember { BringIntoViewRequester() }

    fun validateDataInput(email:String,
                          password: String
    ){
        isFormatValidEmail = ValidatorsFieldsForms.isValidEmail(email)
        isFormatValidPassword = ValidatorsFieldsForms.isValidPassword(password)

        if(isFormatValidEmail && isFormatValidPassword){
            navController.navigate(ScreensNavigate.RegisterEmail.route)
        }
    }


    Background(window)

    Column( modifier= Modifier
        .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {

        Title("Cadastro",
            "Por favor, adicione seus dados a seguir:")

        //CountryCodePick()

        fieldInputText(
            labelName = "Email",
            isValidateField = isFormatValidEmail,
            errorMessage = MessageErrorForBadFormatInFormsFields.EMAIL_FORMAT_BAD,
            value = email,
            onValueChange= {email = it},
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp),
            bringIntoViewRequester = bringIntoViewRequester
        )

        fieldInputTextPassword(
            labelName = "Senha",
            isValidateField = isFormatValidPassword,
            errorMessage = MessageErrorForBadFormatInFormsFields.PASSWORD_FORMAT_BAD,
            value = password,
            onValueChange = {password = it},
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp),
            bringIntoViewRequester = bringIntoViewRequester
        )


        buttonNextPage({validateDataInput(email, password)},bringIntoViewRequester)
    }
}
/*
@Composable
fun CountryCodePick() {

    val context = LocalContext.current
    var phoneCode by remember { mutableStateOf(getDefaultPhoneCode(context)) }
    val fullPhoneNumber = rememberSaveable { mutableStateOf("") }
    val onlyPhoneNumber = rememberSaveable { mutableStateOf("") }




     val phoneNumber = rememberSaveable { mutableStateOf("") }
    fullPhoneNumber.value = getFullPhoneNumber()
    onlyPhoneNumber.value = getOnlyPhoneNumber()

    Log.w("CCP", phoneCode)
    Log.w("CCP", fullPhoneNumber.value)
    Log.w("CCP", onlyPhoneNumber.value)

    TogiCountryCodePicker(
         modifier = Modifier.background(Color.White),
         text = phoneNumber.value,
         shape = RoundedCornerShape(2.dp),
         onValueChange = { phoneNumber.value = it },
         color = Color.White,
         showCountryCode = true,
         showCountryFlag = true,
         focusedBorderColor = Amber,
         unfocusedBorderColor = Amber,
         cursorColor = Color.Black,
     )
}

 */
/*
@Preview(showBackground = true)
@Composable
fun EmailAndPhonePreview() {
    CorridaFacilTheme {
        val window = rememberWindowSize()

        EmailRegister(window, rememberNavController())
    }
}

 */
