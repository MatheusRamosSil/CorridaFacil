package com.example.corridafacil.view.auth.ui.register

import androidx.activity.compose.setContent
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.NavHostController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.corridafacil.utils.responsive.WindowSize
import com.example.corridafacil.utils.validators.errorMessageUI.MessageErrorForBadFormatInFormsFields
import com.example.corridafacil.view.auth.ui.Login
import com.example.corridafacil.view.auth.ui.LoginScreen
import com.example.corridafacil.view.auth.ui.ui.theme.CorridaFacilTheme
import com.example.corridafacil.view.auth.viewModel.EmailViewModel
import io.mockk.mockk
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RegisterEmailKtTestScreen{

    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setUp() {
        val window = mockk<WindowSize>(relaxed = true)
        val navController = mockk<NavHostController>(relaxed = true)
        val viewModel = mockk<EmailViewModel>(relaxed = true)
        composeTestRule.setContent {
            CorridaFacilTheme {
                EmailRegister(window =window,
                    navController = navController,
                    viewModelEmail = viewModel )
            }
        }
    }

    @Test
    fun show_message_error(){
        composeTestRule.onNodeWithText("Email").performTextInput("matheusjramos")
        composeTestRule.onNodeWithText("Senha").performTextInput("matheusjramos")
        composeTestRule.onNode(
            hasText("Proximo") and hasClickAction()
        ).performClick()

        composeTestRule.onNodeWithText(MessageErrorForBadFormatInFormsFields.EMAIL_FORMAT_BAD).assertExists()
        composeTestRule.onNodeWithText(MessageErrorForBadFormatInFormsFields.PASSWORD_FORMAT_BAD).assertExists()
    }

    @Test
    fun success_registering_email_and_password(){
        composeTestRule.onNodeWithText("Email").performTextInput("matheusjramos2000@gmail.com")
        composeTestRule.onNodeWithText("Senha").performTextInput("P@scoa2143")
        composeTestRule.onNode(
            hasText("Proximo") and hasClickAction()
        ).performClick()

        composeTestRule.onNodeWithText(MessageErrorForBadFormatInFormsFields.EMAIL_FORMAT_BAD).assertDoesNotExist()
        composeTestRule.onNodeWithText(MessageErrorForBadFormatInFormsFields.PASSWORD_FORMAT_BAD).assertDoesNotExist()
    }

}