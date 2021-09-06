package com.example.corridafacil.dao

import android.util.Log
import com.example.corridafacil.dao.Implementations.PassageiroDAOImp
import com.example.corridafacil.dao.Implementations.ResultImp
import com.example.corridafacil.Models.Passageiro
import com.google.firebase.database.*


open class PassageiroDAO : PassageiroDAOImp{
    //private var mDatabase: DatabaseReference? = null
    protected var refereceDatabaseFirebaseRealtime = FirebaseDatabase.getInstance().reference

    override fun salvandoPassageiroNoBancoDeDados(passageiro: Passageiro) {
        refereceDatabaseFirebaseRealtime!!.child("Passageiros").child(passageiro.id!!).setValue(passageiro)
    }

    override fun updatePassageiroNoBancoDeDados(passageiro: Passageiro) {
        TODO("Not yet implemented")
    }

    override fun lendoPassageiroNoBancoDeDados(resultImp:ResultImp) {
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                val post = dataSnapshot.getValue().toString()
                resultImp.onSuccess(post)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("TAG", "loadPost:onCancelled", databaseError.toException())
            }
        }
        refereceDatabaseFirebaseRealtime.child("Drivers").child("Location").addValueEventListener(postListener)
    }

    override fun desativandoPassageiroNoBancoDeDados(passageiro: Passageiro) {
        TODO("Not yet implemented")
    }


}