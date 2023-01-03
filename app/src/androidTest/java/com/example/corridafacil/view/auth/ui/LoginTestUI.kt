package com.example.corridafacil.view.auth.ui

import androidx.activity.compose.setContent
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.NavHostController
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.rule.ActivityTestRule
import com.example.corridafacil.utils.responsive.WindowSize
import com.example.corridafacil.utils.validators.errorMessageUI.MessageErrorForBadFormatInFormsFields.EMAIL_FORMAT_BAD
import com.example.corridafacil.utils.validators.errorMessageUI.MessageErrorForBadFormatInFormsFields.PASSWORD_FORMAT_BAD
import com.example.corridafacil.view.auth.ui.ui.theme.CorridaFacilTheme
import com.example.corridafacil.view.auth.viewModel.EmailViewModel
import io.mockk.mockk
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class LoginTestUI {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<Login>()

    @Before
    fun setUp() {
    }

    @Test
    fun show_message_error_to_bad_format_credentials(){
        val window = mockk<WindowSize>(relaxed = true)
        val navController = mockk<NavHostController>(relaxed = true)
        val viewModel = mockk<EmailViewModel>(relaxed = true)
        composeTestRule.activity.setContent {
            CorridaFacilTheme {
                LoginScreen(window =window,
                    navController = navController,
                    viewModelEmail = viewModel )
            }
        }

        composeTestRule.onNodeWithText("Email").performTextInput("matheusjramos")
        composeTestRule.onNodeWithText("Senha").performTextInput("matheusjramos")
        composeTestRule.onNode(
            hasText("Sign in") and hasClickAction()
        ).performClick()

        composeTestRule.onNodeWithText(EMAIL_FORMAT_BAD).assertExists()
        composeTestRule.onNodeWithText(PASSWORD_FORMAT_BAD).assertExists()


    }

    @Test
    fun loging_success_from_map_page(){
        val window = mockk<WindowSize>(relaxed = true)
        val navController = mockk<NavHostController>(relaxed = true)
        val viewModel = mockk<EmailViewModel>(relaxed = true)
        composeTestRule.activity.setContent {
            CorridaFacilTheme {
                LoginScreen(window =window,
                    navController = navController,
                    viewModelEmail = viewModel )
            }
        }

        composeTestRule.onNodeWithText("Email").performTextInput("matheuszeramossilva2000@gmail.com")
        composeTestRule.onNodeWithText("Senha").performTextInput("P@scoa2143")
        composeTestRule.onNode(
            hasText("Sign in") and hasClickAction()
        ).performClick()



    }

    @After
    fun tearDown() {
    }
}