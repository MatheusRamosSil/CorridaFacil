package com.example.corridafacil.data.models.dao

import com.example.corridafacil.data.models.Passageiro
import com.example.corridafacil.utils.firebase.FirebaseConstants
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.getValue
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class PassageiroDAOImpl @Inject constructor() : PassageiroDAO {

    private var refereceDatabaseFirebaseRealtime = FirebaseDatabase.getInstance().reference

    override suspend fun createNewPassageiro(passageiro:Passageiro) = suspendCancellableCoroutine <Boolean>{ continuation ->
        refereceDatabaseFirebaseRealtime.child(FirebaseConstants.DATA_BASE_REALTIME).child(passageiro.id!!).setValue(passageiro)
            .addOnCompleteListener {
                continuation.resume(it.isSuccessful)
            }
            .addOnFailureListener { exception ->
                continuation.resumeWithException(exception)
            }
    }

    override suspend fun readDataUser(userUID: String) = suspendCancellableCoroutine<Passageiro> { continuation ->
        refereceDatabaseFirebaseRealtime.child(FirebaseConstants.DATA_BASE_REALTIME).child(userUID).get()
            .addOnSuccessListener { data ->
                if(data.exists()){

                    data.getValue<Passageiro>()?.let { userData ->
                        continuation.resume(userData)
                    }
                }
            }
            .addOnFailureListener { exception ->
                continuation.resumeWithException(exception)
            }
    }

    override fun disableUser(
        userUID: String,
        callbackActionsDataBase: CallbackActionsDataBase
    ) {
        refereceDatabaseFirebaseRealtime!!.child(FirebaseConstants.DATA_BASE_REALTIME).child(userUID).child("ativado").setValue(false)
            .addOnCompleteListener { res ->
                callbackActionsDataBase.onSuccess(res.isSuccessful)
            }
            .addOnFailureListener { exception ->
                callbackActionsDataBase.onFailure(exception)
            }
    }

    override suspend fun updateUser(userUID: String?, userData: Map<String,String>) = suspendCancellableCoroutine<Boolean> { continuation ->

        refereceDatabaseFirebaseRealtime.child(FirebaseConstants.DATA_BASE_REALTIME).child(userUID!!).updateChildren(userData)
            .addOnCompleteListener { res ->
                continuation.resume(res.isSuccessful)
            }
            .addOnFailureListener { exception ->
                continuation.resumeWithException(exception)
            }
    }

}