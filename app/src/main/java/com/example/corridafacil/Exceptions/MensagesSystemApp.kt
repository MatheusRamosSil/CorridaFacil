package com.example.corridafacil.Exceptions

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.corridafacil.R
import com.google.android.material.snackbar.Snackbar

class MensagesSystemApp : AppCompatActivity() {

 fun showMensage(mensage:String, context:Context){
   Toast.makeText(context,mensage,Toast.LENGTH_LONG).show()
 }

    fun snackbarShow(view: View,stringId:String) {
        Snackbar.make(view, stringId, Snackbar.LENGTH_SHORT)

    }


}