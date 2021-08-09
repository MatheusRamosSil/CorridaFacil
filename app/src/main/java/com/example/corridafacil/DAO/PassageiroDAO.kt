package com.example.corridafacil.DAO

import com.example.corridafacil.DAO.Implementations.PassageiroDAOImp
import com.example.corridafacil.Models.Passageiro
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class PassageiroDAO : PassageiroDAOImp{
    //private var mDatabase: DatabaseReference? = null
    var mDatabase = FirebaseDatabase.getInstance().reference

    override fun salvandoPassageiroNoBancoDeDados(passageiro: Passageiro) {
        mDatabase!!.child("Passageiros").child(passageiro.id!!).setValue(passageiro)
    }

    override fun updatePassageiroNoBancoDeDados(passageiro: Passageiro) {
        TODO("Not yet implemented")
    }

    override fun lendoPassageiroNoBancoDeDados(passageiro: Passageiro) {
        TODO("Not yet implemented")
    }

    override fun desativandoPassageiroNoBancoDeDados(passageiro: Passageiro) {
        TODO("Not yet implemented")
    }


}