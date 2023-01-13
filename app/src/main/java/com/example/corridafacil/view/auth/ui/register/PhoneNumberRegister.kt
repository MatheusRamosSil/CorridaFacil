package com.example.corridafacil.view.auth.ui.register

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.corridafacil.utils.responsive.WindowSize
import com.example.corridafacil.utils.responsive.rememberWindowSize
import com.example.corridafacil.utils.validators.ValidatorsFieldsForms
import com.example.corridafacil.utils.validators.errorMessageUI.MessageErrorForBadFormatInFormsFields
import com.example.corridafacil.view.auth.ui.ui.theme.*

import com.example.corridafacil.view.auth.ui.ui.theme.components.Title
import com.example.corridafacil.view.auth.ui.ui.theme.components.inputOutlinedPhone
import com.example.corridafacil.view.utils.countrypicker.CountryPicker
import com.example.corridafacil.view.utils.countrypicker.getListOfCountries
import com.togitech.ccp.component.*

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PhoneRegister(window: WindowSize,
                          navController: NavHostController
) {


    var phoneNumber by remember { mutableStateOf("") }
    var countryCodePicker by remember { mutableStateOf("") }
    val countryCode = CountryPicker()

    var isFormatValidPhone by remember { mutableStateOf(true) }

    fun validateFieldPhoneNumber() {
        val codePickerPlusPhoneNumber = countryCodePicker+phoneNumber
        isFormatValidPhone = ValidatorsFieldsForms.isValidPhoneNumber(codePickerPlusPhoneNumber)

        if (isFormatValidPhone){
            TODO("Not yet implemented")
        }else{

        }
    }

    Background(window)

    Column( modifier= Modifier
        .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {

        Title("Cadastro",
            "Por favor, adicione seus dados a seguir:")

        Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start) {
            Row( modifier = Modifier.padding(24.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center) {


                countryCode.CountryCodeDialog(
                    pickedCountry = {
                        countryCodePicker = it.countryPhoneCode
                        Log.v("TAG", "country name is : ${it.countryPhoneCode}")
                    },
                    defaultSelectedCountry = getListOfCountries().single { it.countryCode == "us" },
                    dialogSearch = true,
                    dialogRounded = 4
                )

                inputOutlinedPhone(
                    labelName = "Telefone",
                    value = phoneNumber,
                    onValueChange= {phoneNumber = it},
                    modifier = Modifier.padding(start = 16.dp),
                )
            }
            if(!isFormatValidPhone){
                Text(text = MessageErrorForBadFormatInFormsFields.PHONE_BAD_FORMAT,
                    color = Color.Red,
                    style = MaterialTheme.typography.overline,
                    modifier = Modifier.padding(start = 24.dp)
                )
            }

        }


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ){
            Text(text = "Ao cria uma conta, você concorda com os nossos",
                style = MaterialTheme.typography.body2)
            Text(text = "Termos de serviços e Políticas de privacidade ",
                style = MaterialTheme.typography.h3)
        }

        buttonNextPage(click = { validateFieldPhoneNumber() })
    }


}

@Preview(showBackground = true)
@Composable
fun RegisterPhonePreview() {
    CorridaFacilTheme {
        val window = rememberWindowSize()

        PhoneRegister(window, rememberNavController())
    }
}


