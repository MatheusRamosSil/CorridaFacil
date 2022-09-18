package com.example.corridafacil.view.auth.viewModel

import androidx.lifecycle.MutableLiveData
import com.example.corridafacil.R
import com.example.corridafacil.view.auth.ui.componentsView.ComponentsViewActivity
import com.example.corridafacil.utils.validators.ValidatorsFieldsForms.isValidEmail
import com.example.corridafacil.utils.validators.ValidatorsFieldsForms.isValidFormatName
import com.example.corridafacil.utils.validators.ValidatorsFieldsForms.isValidPassword
import com.example.corridafacil.utils.validators.errorMessageUI.MessageErrorForBadFormatInFormsFields
import com.example.corridafacil.utils.validators.errorMessageUI.ShowMessageErrorBadFormart.showMensageErrorFormaBad

class ViewValidators(private val componentsViewActivity: ComponentsViewActivity) {

    val checkFieldsIsValid = MutableLiveData<Boolean>()

    private val formatIsValidFormFields = HashMap<String,Boolean>()

    fun validateFieldsFormAddEmail(){
        formatIsValidFormFields.put("nome" , isValidFormatName(componentsViewActivity.getValueFieldEdtiTextToString(R.id.editTextNome)))
        formatIsValidFormFields.put("sobrenome",isValidFormatName(componentsViewActivity.getValueFieldEdtiTextToString(R.id.editTextSobrenome)))
        formatIsValidFormFields.put("email", isValidEmail(componentsViewActivity.getValueFieldEdtiTextToString(R.id.editTextFormEmail)))
        formatIsValidFormFields.put("senha", isValidPassword(componentsViewActivity.getValueFieldEdtiTextToString(R.id.editTextFormPassword)))
        formatIsValidFormFields.put("imageProfile",checkIfSelectedImageProfile(R.id.imageFormProfile))

        checkFieldsIsValid.value = formatIsValidFormFields.containsValue(false)
    }

    fun showMenssageErrorToFormatFields(){
        formatIsValidFormFields["nome"]!!.showMensageErrorFormaBad(componentsViewActivity.getComponentEditText(
            R.id.editTextNome), MessageErrorForBadFormatInFormsFields.NAME_FORMAT_BAD)
        formatIsValidFormFields["sobrenome"]!!.showMensageErrorFormaBad(componentsViewActivity.getComponentEditText(
            R.id.editTextSobrenome), MessageErrorForBadFormatInFormsFields.LAST_NAME_FORMAT_BAD)
        formatIsValidFormFields["email"]!!.showMensageErrorFormaBad(componentsViewActivity.getComponentEditText(
            R.id.editTextFormEmail),MessageErrorForBadFormatInFormsFields.EMAIL_FORMAT_BAD)
        formatIsValidFormFields["senha"]!!.showMensageErrorFormaBad(componentsViewActivity.getComponentEditText(
            R.id.editTextFormPassword),MessageErrorForBadFormatInFormsFields.PASSWORD_FORMAT_BAD)
        checkIfSelectedImageProfile(R.id.imageFormProfile)
    }

    fun checkIfSelectedImageProfile(componentId: Int): Boolean {
        val valueSelected = componentsViewActivity.getComponentImageView(componentId)
        if (valueSelected.drawable != null){
            return true
        }
        exibirMensagemDeErroParaImageProfile()
        return false
    }

    private fun exibirMensagemDeErroParaImageProfile() {
        val mensagemDeErroParaImageProfile = componentsViewActivity.getComponentTextView(R.id.messageErrorOfImageProfile)
        mensagemDeErroParaImageProfile.requestFocus()
        mensagemDeErroParaImageProfile.error = "Por favor adicione uma foto para o perfil"

    }
}