package com.example.corridafacil.Controller

import android.net.Uri
import android.widget.Toast
import com.example.corridafacil.Controller.FactoryPassageiro.PassageiroControllersImp
import com.example.corridafacil.Models.Passageiro
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.UploadTask
import java.io.File


class PassageiroControllers : PassageiroControllersImp{
    private var mDatabase: DatabaseReference? = null

    /*fun salvarPassageiroNoBancoDeDados(userId: String?, passageiro: Passageiro?) {
        mDatabase = FirebaseDatabase.getInstance().reference
        mDatabase!!.child("Passageiros").child(userId!!).setValue(passageiro)
    }

     */

    override fun salvandoPassageiroNoBancoDeDados(userId: String?, passageiro: Passageiro) {
        mDatabase = FirebaseDatabase.getInstance().reference
        mDatabase!!.child("Passageiros").child(userId!!).setValue(passageiro)
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