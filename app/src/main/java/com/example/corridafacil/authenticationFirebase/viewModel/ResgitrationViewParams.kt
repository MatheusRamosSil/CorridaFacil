package com.example.corridafacil.authenticationFirebase.viewModel

import com.example.corridafacil.models.Passageiro

class ResgitrationViewParams(val valuesFromUserDriver: Map<String,String>, val statusActivated: Boolean) {
    fun create(): Passageiro {
        val userDriver = Passageiro()
        userDriver.id = valuesFromUserDriver["uidUser"]
        userDriver.nome = valuesFromUserDriver["nome"]
        userDriver.sobrenome = valuesFromUserDriver["sobrenome"]
        userDriver.email = valuesFromUserDriver["email"]
        userDriver.senha = valuesFromUserDriver["senha"]
        userDriver.telefone = valuesFromUserDriver["telefone"]
        userDriver.foto = valuesFromUserDriver["urlDaFoto"]
        userDriver.tokenPassageiro = valuesFromUserDriver["token"]
        userDriver.ativado = statusActivated

        return userDriver
    }
}