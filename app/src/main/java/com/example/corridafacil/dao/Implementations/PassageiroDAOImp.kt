package com.example.corridafacil.dao.Implementations

import com.example.corridafacil.Models.Passageiro

interface PassageiroDAOImp {
    fun salvandoPassageiroNoBancoDeDados(passageiro: Passageiro)
    fun updatePassageiroNoBancoDeDados(passageiro: Passageiro)
    fun lendoPassageiroNoBancoDeDados(resultImp:ResultImp)
    fun desativandoPassageiroNoBancoDeDados(passageiro: Passageiro)
}