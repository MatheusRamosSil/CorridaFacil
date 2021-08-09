package com.example.corridafacil.Components.Alerts

import android.content.Context
import android.content.Intent
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat.startActivity

/*
private fun showAlert() {
    val dialog = AlertDialog.Builder(this)
    dialog.setTitle("Ative sua localização")
        .setMessage("A localização do seu dispositivo deve estar 'desligada'.\nPor favor ative sua localização")
        .setPositiveButton("Abrir configurações") { paramDialogInterface, paramInt ->
            val myIntent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            startActivity(myIntent)
        }
        .setNegativeButton("Cancel") { paramDialogInterface, paramInt -> }
    dialog.show()
}

 */

fun Context.exibirAlertaDeCofiguracoesDeGPS() = AlertDialog.Builder(this)
        .setTitle("Ative sua localização")
        .setMessage("A localização do seu dispositivo deve estar 'desligada'.\n" +
                     "Por favor ative sua localização")
        .setPositiveButton("Abrir configurações"){ paramDialogInterface, paramInt ->
            startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
        }
        .setNegativeButton("Cancel"){paramDialogInterface, paramInt -> }
        .show()