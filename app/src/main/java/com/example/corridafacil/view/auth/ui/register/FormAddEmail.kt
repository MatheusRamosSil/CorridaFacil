package com.example.corridafacil.view.auth.ui.register

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.corridafacil.R
import com.example.corridafacil.domain.services.AuthenticationFirebaseSevice.Email.AuthenticationEmailFirebaseServiceImpl
import com.example.corridafacil.domain.services.FirebaseCloudStorage.FirebaseStorageCloud
import com.example.corridafacil.data.repository.auth.email.EmailRepositoryImpl
import com.example.corridafacil.view.auth.ui.Login
import com.example.corridafacil.view.auth.ui.componentsView.ComponentsViewActivity
import com.example.corridafacil.view.auth.viewModel.EmailViewModel
import com.example.corridafacil.view.auth.viewModel.Result
import com.example.corridafacil.view.auth.viewModel.ViewValidators
import com.example.corridafacil.view.auth.viewModel.factories.ViewModelEmailFactory
import com.example.corridafacil.databinding.ActivityFormAddEmailBinding
import com.example.corridafacil.data.models.dao.PassageiroDAOImpl
import com.example.corridafacil.utils.permissions.Permissions
import com.example.corridafacil.utils.permissions.Permissions.checkForPermissions
import com.example.corridafacil.utils.permissions.Permissions.hasReadExternalStoragePermission
import com.example.corridafacil.view.auth.viewModel.AuthenticathionEmail


class FormAddEmail : AppCompatActivity() {

    private lateinit var binding: ActivityFormAddEmailBinding
    private lateinit var viewModelEmail: EmailViewModel
    private lateinit var viewValidators: ViewValidators
    private lateinit var componentsViewFormAddEmail: ComponentsViewActivity
    private lateinit var imagemDoPerfil: Uri
    private var fieldsForm = HashMap<String,String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_add_email)
        binding = ActivityFormAddEmailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModelEmail =
            ViewModelProvider(this, ViewModelEmailFactory(
                EmailRepositoryImpl(PassageiroDAOImpl(),
                    AuthenticationEmailFirebaseServiceImpl(),
                    FirebaseStorageCloud()
                ),
            )
            )
                .get(EmailViewModel::class.java)
        binding.viewmodel = viewModelEmail

        viewValidators = ViewValidators(ComponentsViewActivity(this))
        componentsViewFormAddEmail = ComponentsViewActivity(this)
    }

    override fun onStart() {
        super.onStart()
        binding.imageFormProfile.setOnClickListener{activedPermissionsAcessLocalFilesDevice()}
        binding.buttonFormAddEmail.setOnClickListener{toPageMapa()}
    }

    fun activedPermissionsAcessLocalFilesDevice() {
        if(hasReadExternalStoragePermission()){
            abrirAGaleriaDeImagensDispositivoDoUsuario()
        }else{
            checkForPermissions(this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                "files",
                Permissions.PERMISSION_READ_EXTERNAL_STORAGE)
        }
    }

    fun abrirAGaleriaDeImagensDispositivoDoUsuario() {
        selectPictureLauncher.launch("image/*")
    }

    private val selectPictureLauncher = registerForActivityResult(ActivityResultContracts.GetContent()){ uri ->
        if (uri != null){
            imagemDoPerfil = uri
            binding.imageFormProfile.setImageURI(imagemDoPerfil)
        }else{
            viewValidators.checkIfSelectedImageProfile(R.id.imageFormProfile)
        }
    }



    override fun onResume() {
        super.onResume()

        viewValidators.checkFieldsIsValid.observe(this, Observer {
            if (it == false) {
                viewModelEmail.addValuesToOneNewUser = adicionandoValoresParaNovoUsuario()

                viewModelEmail.register(
                    imagemDoPerfil,
                    componentsViewFormAddEmail.getValueFieldEdtiTextToString(R.id.editTextFormEmail),
                    componentsViewFormAddEmail.getValueFieldEdtiTextToString(R.id.editTextFormPassword)
                )

              lifecycleScope.launchWhenStarted {
                  viewModelEmail.stateUI.collect {
                      when(it){
                          is Result.Success ->{
                              startActivity(Intent(this@FormAddEmail, Login::class.java))
                          }
                          is Result.Error ->{
                              Toast.makeText(this@FormAddEmail, it.exception.message.toString(), Toast.LENGTH_SHORT).show()
                          }
                          else -> Unit
                      }
                  }
              }

            }
            viewValidators.showMenssageErrorToFormatFields()
        })
    }

    private fun adicionandoValoresParaNovoUsuario(): HashMap<String, String> {
        fieldsForm.put("nome",componentsViewFormAddEmail.getValueFieldEdtiTextToString(R.id.editTextNome))
        fieldsForm.put("sobrenome",componentsViewFormAddEmail.getValueFieldEdtiTextToString(R.id.editTextSobrenome))
        fieldsForm.put("email",componentsViewFormAddEmail.getValueFieldEdtiTextToString(R.id.editTextFormEmail))
        fieldsForm.put("senha",componentsViewFormAddEmail.getValueFieldEdtiTextToString(R.id.editTextFormPassword))
        fieldsForm.put("telefone",intent.getStringExtra("numeroTelefoneDoUsuario").toString())

        return fieldsForm
    }


    fun toPageMapa(){
        validateFieldsForms()
    }

    private fun validateFieldsForms() {
        viewValidators.validateFieldsFormAddEmail()

    }
}