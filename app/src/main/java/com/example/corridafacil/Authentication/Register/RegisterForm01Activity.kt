package com.example.corridafacil.Authentication.Register

import android.Manifest
import android.R.attr.bitmap
import android.app.Activity
import android.content.CursorLoader
import android.content.Intent
import android.database.Cursor
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.*
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import com.example.corridafacil.R
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.google.firebase.storage.ktx.storage
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import java.io.File
import java.io.InputStream


class RegisterForm01Activity : AppCompatActivity() {
    private lateinit var getNome:EditText
    private lateinit var getSobrenome:EditText
    private lateinit var getEmail:EditText
    private lateinit var getPassword:EditText
    private lateinit var imageView:ImageButton
    private lateinit var buttonNextPage:Button
    var storage: FirebaseStorage = FirebaseStorage.getInstance()
    private lateinit var storageRef:StorageReference

    private lateinit var  selectedImage: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_form01)
        startVariables()
    }

    private fun startVariables() {
         val storage = Firebase.storage
         storageRef = storage.reference
         getNome = findViewById(R.id.editTextNome)
         getSobrenome = findViewById(R.id.editTextSobrenome)
         getEmail = findViewById(R.id.editTextEmail)
         getPassword = findViewById(R.id.editTextPassword)
         imageView = findViewById(R.id.imageButton)
         buttonNextPage = findViewById(R.id.button)
         buttonNextPage.setOnClickListener {

        }
        imageView.setOnClickListener {
             permissaoDeAcessoAArquivos()
        }

    }

    private fun permissaoDeAcessoAArquivos() {
        Dexter.withActivity(this)
            .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(permissionGrantedResponse: PermissionGrantedResponse) {
                    abrirAGaleriaDeImagensDispositivoDoUsuario()
                }

                override fun onPermissionDenied(permissionDeniedResponse: PermissionDeniedResponse) {}
                override fun onPermissionRationaleShouldBeShown(
                    permissionRequest: PermissionRequest?,
                    permissionToken: PermissionToken
                ) {
                    permissionToken.continuePermissionRequest()
                }
            }).check()
    }

    private fun abrirAGaleriaDeImagensDispositivoDoUsuario() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(Intent.createChooser(intent, "Abrindo galeria"), 1)
    }

    private fun uploadDaImagemParaOStorage(myFile:File) {
        val file: Uri = Uri.fromFile(myFile)
        val riversRef = storageRef.child("passageirosFotosPerfil/" + file.getLastPathSegment())
        var uploadTask = riversRef.putFile(file)
        // Register observers to listen for when the download is done or if it fails
        uploadTask.addOnFailureListener(OnFailureListener {
            // Handle unsuccessful uploads
        }).addOnSuccessListener(OnSuccessListener<UploadTask.TaskSnapshot?> {
            riversRef.downloadUrl.addOnSuccessListener { uri -> // Got the download URL for 'users/me/profile.png'
                val getUrlImage = uri.toString()
                Toast.makeText(this, "Upload image OK", Toast.LENGTH_LONG)
                    .show()
            }.addOnFailureListener { e ->
                Toast.makeText(this,e.message,Toast.LENGTH_LONG).show()
            }
        })

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            //the image URI
            if (data != null) {
                selectedImage = data.data!!
                getRealPathFromURI(selectedImage)
                try {
                    showImagemSelecionada(contentResolver.openInputStream(selectedImage))
                } catch (e: Exception) {
                }
            }

        }
        super.onActivityResult(requestCode, resultCode, data)
    }


    private fun showImagemSelecionada(inputStream: InputStream?) {
        val bitmap = BitmapFactory.decodeStream(inputStream)
        imageView.setImageBitmap(bitmap)
    }

    // Função para obter o caminho da imagem selecionadas
    private fun getRealPathFromURI(contentUri: Uri) {
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val loader = CursorLoader(this, contentUri, proj, null, null, null)
        val cursor: Cursor = loader.loadInBackground()
        val column_index: Int = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor.moveToFirst()
        val result: String = cursor.getString(column_index)
        cursor.close()
        uploadDaImagemParaOStorage(File(result))
    }

}