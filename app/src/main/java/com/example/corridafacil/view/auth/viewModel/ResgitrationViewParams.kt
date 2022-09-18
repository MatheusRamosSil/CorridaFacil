package com.example.corridafacil.view.auth.viewModel

import com.example.corridafacil.data.models.Passageiro

class ResgitrationViewParams(val valuesFromUserDriver: Map<String,String>, val statusActivated: Boolean) {

    fun create(): Passageiro {
        val userDriver = Passageiro()
        userDriver.id = valuesFromUserDriver["uidUser"]
        userDriver.nome = valuesFromUserDriver["nome"]
        userDriver.sobrenome = valuesFromUserDriver["sobrenome"]
        userDriver.email = valuesFromUserDriver["email"]
        userDriver.tipoDeDispositivo = valuesFromUserDriver["tipoDeDispositivo"]
        userDriver.modeloDoDispositivo = valuesFromUserDriver["modeloDoDispositivo"]
        userDriver.telefone = valuesFromUserDriver["telefone"]
        userDriver.foto = valuesFromUserDriver["urlDaFoto"]
        userDriver.tokenPassageiro = valuesFromUserDriver["token"]
        userDriver.ativado = statusActivated

        return userDriver
    }
}