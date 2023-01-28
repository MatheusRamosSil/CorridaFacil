package com.example.corridafacil.view.auth.ui.register

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.corridafacil.utils.responsive.WindowSize
import com.example.corridafacil.utils.responsive.rememberWindowSize
import com.example.corridafacil.view.auth.ui.ui.theme.Amber
import com.example.corridafacil.view.auth.ui.ui.theme.Background
import com.example.corridafacil.view.auth.ui.ui.theme.CorridaFacilTheme
import com.example.corridafacil.view.auth.ui.ui.theme.components.Title
import com.example.corridafacil.view.auth.viewModel.PhoneViewModel
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CodeOTPScreen(
    window: WindowSize,
    navController: NavHostController,
    viewModel: PhoneViewModel=hiltViewModel(),
    ) {

    val coroutineScope = rememberCoroutineScope()
    val nameFieldViewRequester = remember { BringIntoViewRequester() }
    var isValidateField by remember { mutableStateOf(true) }
    var  otpValue by remember { mutableStateOf("") }



    fun validateFieldPhoneNumber() {
        val uiState by viewModel.uiState
        if (otpValue.equals("")){
            isValidateField = false
            return
        }else{
            coroutineScope.launch {
                val credential = PhoneAuthProvider.getCredential(uiState.codeId, otpValue)
                val result = viewModel.validateCodeOTPWithSingInPhone(credential)
                Log.w("My user", "${result?.uid}")
            }
        }
    }

    Background(window)

    Column( modifier= Modifier
        .fillMaxSize()
        .bringIntoViewRequester(nameFieldViewRequester),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {

        Title("Cadastro",
            "Aguarde, estamos realizanod uma verificação:")

        Column(
            modifier = Modifier
                .padding(24.dp),
        ){
            Text(text = "Entre com o código OTP",
                style = MaterialTheme.typography.subtitle2)

                Text( modifier = Modifier.padding(top = 8.dp),
                    text = "Por favor, digite os 4 dígitos do SMS que enviamos para +55 11 999 00. ",
                    style = MaterialTheme.typography.h5)
                Text(text = "Esse não é seu número correto de telefone?",
                    style = MaterialTheme.typography.h4)
        }

        FieldCodeOTP(value= otpValue,
                     onValueChange= {otpValue = it},
                     messageError= "Insira o codigo de verificação",
                     bringIntoViewRequester = nameFieldViewRequester,
                     isValidateField = isValidateField)

        buttonNextPage(click = { validateFieldPhoneNumber() },
                        bringIntoViewRequester = nameFieldViewRequester)
        ResendCodeOTP()


    }
}

@Composable
fun ResendCodeOTP() {
    Row(modifier = Modifier.padding(top = 12.dp)){
        Text(text = "Não recebi o código.",
            style = MaterialTheme.typography.overline,
            fontSize = 14.sp)
        Text(text = " Reenviar novamente",
            modifier = Modifier.clickable {

            },
            style = MaterialTheme.typography.h3,
            color = MaterialTheme.colors.onSurface)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FieldCodeOTP(
    bringIntoViewRequester: BringIntoViewRequester = remember { BringIntoViewRequester() },
    messageError: String,
    onValueChange: (String) -> Unit,
    value: String,
    isValidateField:Boolean
) {

    val coroutineScope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current

    BasicTextField(value = value,
                    modifier = Modifier
                        .onFocusEvent { focusState ->
                            if(focusState.isFocused){
                                coroutineScope.launch {
                                    bringIntoViewRequester.bringIntoView()
                                }
                            }
                        },
                   onValueChange = {
                       if (it.length <= 6){
                           onValueChange(it)
                       }
                   },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.NumberPassword
                    ),
                    keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                decorationBox = {
                    Row(modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically) {
                        repeat(6){ index ->
                            val char = when {
                                index >= value.length -> ""
                                else -> value[index].toString()
                            }
                            Text(
                                modifier = Modifier
                                    .width(42.dp)
                                    .height(50.dp)
                                    .border(1.dp, Amber, RoundedCornerShape(8.dp))
                                    .padding(2.dp),
                                text = char,
                                style = MaterialTheme.typography.h1,
                                color = Color.DarkGray,
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                        }

                    }
                }

    )

    if(!isValidateField){
        Text(text = messageError,
            modifier= Modifier.padding(12.dp),
            color = Color.Red,
            style = MaterialTheme.typography.overline
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterCodeOTPPreview() {
    CorridaFacilTheme {
        val window = rememberWindowSize()

        //CodeOTPScreen(window, rememberNavController(), registeCodeOTPActivity = this)
    }
}