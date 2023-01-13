package com.example.corridafacil.view.auth.ui.register

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.corridafacil.R
import com.example.corridafacil.utils.responsive.WindowSize
import com.example.corridafacil.utils.validators.ValidatorsFieldsForms
import com.example.corridafacil.utils.validators.errorMessageUI.MessageErrorForBadFormatInFormsFields
import com.example.corridafacil.view.auth.ui.ui.theme.*
import com.example.corridafacil.view.auth.ui.ui.theme.components.Title
import com.example.corridafacil.view.auth.ui.ui.theme.components.fieldInputText
import com.example.corridafacil.view.auth.viewModel.EmailViewModel
import com.example.corridafacil.view.utils.ScreensNavigate

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProfileRegisterScreen(
    window: WindowSize,
    navController: NavHostController,
    viewModelEmail: EmailViewModel
) {

    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }

    var isFormatValidFirstName by remember { mutableStateOf(true) }
    var isFormatValidLastName by remember { mutableStateOf(true) }

    val nameFieldViewRequester = remember { BringIntoViewRequester() }

    fun validateDataInput(firstName:String,
                          lastName: String
    ) {
        isFormatValidFirstName = ValidatorsFieldsForms.isValidFormatName(firstName)
        isFormatValidLastName = ValidatorsFieldsForms.isValidFormatName(lastName)

        if(isFormatValidFirstName && isFormatValidLastName){
            navController.navigate(ScreensNavigate.RegisterEmail.route)
        }
    }

    Background(window)

    Column( modifier= Modifier
        .fillMaxSize()
        .bringIntoViewRequester(nameFieldViewRequester),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally){

        Title("Cadastro",
            "Por favor, adicione seus dados a seguir:")

        fieldProfile()


        fieldInputText(
            labelName = "Nome",
            isValidateField = isFormatValidFirstName,
            errorMessage = MessageErrorForBadFormatInFormsFields.NAME_FORMAT_BAD,
            value = firstName,
            onValueChange= {firstName = it},
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp),
            bringIntoViewRequester = nameFieldViewRequester
        )

        fieldInputText(
            labelName = "Sobrenome",
            isValidateField = isFormatValidLastName,
            errorMessage = MessageErrorForBadFormatInFormsFields.NAME_FORMAT_BAD,
            value = lastName,
            onValueChange= {lastName = it},
            bringIntoViewRequester = nameFieldViewRequester,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp),
        )

        buttonNextPage({validateDataInput(firstName, lastName)},nameFieldViewRequester)
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



@OptIn(ExperimentalFoundationApi::class)
@Composable
fun buttonNextPage(click: () -> Unit,
                   bringIntoViewRequester: BringIntoViewRequester = remember { BringIntoViewRequester() }) {

   Box(modifier = Modifier
       .padding(26.dp)
       .height(64.dp)
       .width(287.dp)
       .clip(RoundedCornerShape(10.dp))
       .background(YellowGradientCircle)
       .bringIntoViewRequester(bringIntoViewRequester)
       .clickable { click() }) {
        Row(verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)) {
            Text("Proximo",
                style = MaterialTheme.typography.h3,
                color = Color.Black,
                fontSize = 14.sp)
            Icon(painter = painterResource(id = R.drawable.arrow),
                contentDescription = "Arrow")
        }


    }
}


