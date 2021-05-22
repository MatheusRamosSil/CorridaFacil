package com.example.corridafacil.Controller.FactoryPassageiro

import com.example.corridafacil.Models.Passageiro

interface PassageiroControllersImp {
    fun salvandoPassageiroNoBancoDeDados(userId: String?,passageiro: Passageiro)
    fun updatePassageiroNoBancoDeDados(passageiro: Passageiro)
    fun lendoPassageiroNoBancoDeDados(passageiro: Passageiro)
    fun desativandoPassageiroNoBancoDeDados(passageiro: Passageiro)
}