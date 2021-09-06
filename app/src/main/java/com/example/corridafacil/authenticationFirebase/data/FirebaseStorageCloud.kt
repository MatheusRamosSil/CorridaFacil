package com.example.corridafacil.authenticationFirebase.data

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.google.firebase.storage.ktx.storage


class FirebaseStorageCloud {
    var storage = FirebaseStorage.getInstance()
    val storageReference = Firebase.storage.reference



    fun fazerUploadDaImagemEPegarAUrl(callback: FirebaseStorageCallbackImp, selectedImage:Uri){
        val riversRef = storageReference.child("passageirosFotosPerfil/" + selectedImage.lastPathSegment)
        riversRef.putFile(selectedImage).addOnSuccessListener {
            riversRef.downloadUrl.addOnSuccessListener { uri ->
               // this.urlResultImage = uri.toString()
                callback.pegarAUrlAposUplaod(uri.toString())
                Log.i("UPLOAD SUCESSO","tudo certo upload")
            }
        }
            .addOnFailureListener {
                Log.i("Errado ",it.message.toString())
            }

    }













    }