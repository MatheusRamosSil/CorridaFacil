package com.example.corridafacil.DAO.Implementations

import com.example.corridafacil.Models.Passageiro

interface PassageiroDAOImp {
    fun salvandoPassageiroNoBancoDeDados(passageiro: Passageiro)
    fun updatePassageiroNoBancoDeDados(passageiro: Passageiro)
    fun lendoPassageiroNoBancoDeDados(passageiro: Passageiro)
    fun desativandoPassageiroNoBancoDeDados(passageiro: Passageiro)
}