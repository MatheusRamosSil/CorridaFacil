package com.example.corridafacil.view.auth.viewModel.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.corridafacil.data.repository.auth.email.EmailRepository
import com.example.corridafacil.view.auth.viewModel.EmailViewModel

class ViewModelEmailFactory constructor(private val emailRepository: EmailRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(EmailViewModel::class.java)) {
            EmailViewModel(emailRepository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}
