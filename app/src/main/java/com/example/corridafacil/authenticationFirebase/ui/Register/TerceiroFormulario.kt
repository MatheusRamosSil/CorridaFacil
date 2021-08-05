package com.example.corridafacil.authenticationFirebase.ui.Register

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.corridafacil.R
import com.example.corridafacil.Services.AuthenticationFirebaseSevice.AuthenticationEmailFirebase
import com.example.corridafacil.authenticationFirebase.Repository.EmailRepository
import com.example.corridafacil.authenticationFirebase.ui.LoginActivity
import com.example.corridafacil.authenticationFirebase.viewModel.EmailViewModel
import com.example.corridafacil.authenticationFirebase.viewModel.EmailViewModelFactory
import com.example.corridafacil.databinding.ActivityRegisterForm01Binding
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.storage.FirebaseStorage
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.single.PermissionListener
import java.io.InputStream


class TerceiroFormulario : AppCompatActivity() {

    var storage = FirebaseStorage.getInstance()
    private lateinit var  imagemEscolhida: Uri
    private lateinit var binding: ActivityRegisterForm01Binding
    lateinit var emailViewModel: EmailViewModel
    var authenticationEmailFirebase = AuthenticationEmailFirebase.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_form01)
        binding = ActivityRegisterForm01Binding.inflate(layoutInflater)
        setContentView(binding.root)

        emailViewModel = ViewModelProvider(this,EmailViewModelFactory(
            EmailRepository(authenticationEmailFirebase)
        )).get(EmailViewModel::class.java)
        binding.viewmodel = emailViewModel
    }

    override fun onStart() {
        super.onStart()
        binding.button.setOnClickListener(this::buttonNextPage)
        binding.imageView2.setOnClickListener(this::activedPermissionsAcessLocalFilesDevice)
    }

    fun activedPermissionsAcessLocalFilesDevice(v: View){
        permissaoDeAcessoAArquivos()
    }

    fun permissaoDeAcessoAArquivos() {
        Dexter.withContext(this).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE).withListener(object :
            PermissionListener {
            override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                abrirAGaleriaDeImagensDispositivoDoUsuario()
            }

            override fun onPermissionRationaleShouldBeShown(p0: com.karumi.dexter.listener.PermissionRequest?, p1: PermissionToken?) {
                TODO("Not yet implemented")
            }

            override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
                TODO("Adicionar uma exception")

            }

        }).check()

    }

    fun abrirAGaleriaDeImagensDispositivoDoUsuario() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(Intent.createChooser(intent, "Abrindo galeria"), 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null && data.data != null){
            //the image URI
            imagemEscolhida = data.data!!
            exibirImagemSelecionada(contentResolver.openInputStream(imagemEscolhida))
            binding.viewmodel?.imagemEscolhida = imagemEscolhida
            binding.viewmodel?.fazerUploadDaImagemEPegarAUrl()
        }
        super.onActivityResult(requestCode, resultCode, data)
    }


    private fun exibirImagemSelecionada(inputStream: InputStream?) {
        val bitmap = BitmapFactory.decodeStream(inputStream)
        binding.imageView2.setImageBitmap(bitmap)
    }

    fun buttonNextPage(v:View) {
        registerNewUser()
        irParaPaginaDeLogin()
    }

    private fun registerNewUser() {
        adicionarValoresAoCamposPassageiro()
        binding.viewmodel?.register()
    }

    private fun adicionarValoresAoCamposPassageiro() {
        binding.viewmodel?.passageiro?.nome = binding.editTextNome.text.toString()
        binding.viewmodel?.passageiro?.sobrenome = binding.editTextSobrenome.text.toString()
        binding.viewmodel?.passageiro?.senha = binding.editTextPassword.text.toString()
        binding.viewmodel?.passageiro?.email = binding.editTextEmail.text.toString()
        binding.viewmodel?.passageiro?.telefone = intent.getStringExtra("telefone").toString()
        binding.viewmodel?.passageiro?.ativado = true
        binding.viewmodel?.passageiro?.tokenPassageiro = FirebaseInstanceId.getInstance().token.toString()
    }

    fun irParaPaginaDeLogin(){
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }



}




