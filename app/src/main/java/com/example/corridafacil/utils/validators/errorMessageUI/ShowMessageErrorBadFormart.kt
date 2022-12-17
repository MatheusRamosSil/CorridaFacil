package com.example.corridafacil.utils.validators.errorMessageUI


import android.widget.EditText

object ShowMessageErrorBadFormart {

    fun Boolean.showMensageErrorFormaBad(componenteEditText: EditText?, mensagemDeErro : String){
        if (!this){
            componenteEditText!!.error = mensagemDeErro
        }
    }
}