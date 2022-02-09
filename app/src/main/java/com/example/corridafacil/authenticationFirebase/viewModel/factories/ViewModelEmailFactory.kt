package com.example.corridafacil.authenticationFirebase.viewModel.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.corridafacil.authenticationFirebase.repository.email.EmailRepository
import com.example.corridafacil.authenticationFirebase.viewModel.EmailViewModel

class ViewModelEmailFactory constructor(private val emailRepository: EmailRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(EmailViewModel::class.java)) {
            EmailViewModel(emailRepository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}
