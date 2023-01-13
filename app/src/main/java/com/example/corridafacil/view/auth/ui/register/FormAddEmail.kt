package com.example.corridafacil.view.auth.ui.register

import android.net.Uri
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.corridafacil.R
import com.example.corridafacil.databinding.ActivityFormAddEmailBinding
import com.example.corridafacil.utils.permissions.Permissions
import com.example.corridafacil.utils.permissions.Permissions.checkForPermissions
import com.example.corridafacil.utils.permissions.Permissions.hasReadExternalStoragePermission
import com.example.corridafacil.view.auth.ui.componentsView.ComponentsViewActivity
import com.example.corridafacil.view.auth.viewModel.EmailViewModel
import com.example.corridafacil.view.auth.viewModel.ViewValidators
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FormAddEmail : AppCompatActivity() {

    private lateinit var binding: ActivityFormAddEmailBinding
    private  val viewModelEmail: EmailViewModel by viewModels()
    private lateinit var viewValidators: ViewValidators
    private lateinit var componentsViewFormAddEmail: ComponentsViewActivity
    private lateinit var imagemDoPerfil: Uri
    private var fieldsForm = HashMap<String,String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_add_email)
        binding = ActivityFormAddEmailBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

       /* viewValidators.checkFieldsIsValid.observe(this, Observer {
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

        */
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