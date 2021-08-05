package com.example.corridafacil.authenticationFirebase.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.corridafacil.authenticationFirebase.Repository.EmailRepository

class EmailViewModelFactory constructor(private val emailRepository: EmailRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(EmailViewModel::class.java)) {
            EmailViewModel(this.emailRepository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }

}