package com.example.corridafacil.Services.FirebaseCloudStorage

import android.net.Uri
import com.example.corridafacil.utils.firebase.FirebaseConstants
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.tasks.await

class FirebaseStorageCloud {

    val storageReference = Firebase.storage.reference

    suspend fun uploadImageProfile(selectedImage: Uri, uidUser:String): String {
        val namePhotoProfile = "Avatar_" + uidUser
        val riverRef = storageReference.child("${FirebaseConstants.STORAGE_CLOUD_PHOTOS_USER}/$uidUser/$namePhotoProfile")
        val urlImage = riverRef.putFile(selectedImage).await().storage.downloadUrl.await().toString()
        return urlImage
    }

}