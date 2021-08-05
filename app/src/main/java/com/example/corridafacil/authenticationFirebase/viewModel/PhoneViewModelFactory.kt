package com.example.corridafacil.authenticationFirebase.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.corridafacil.authenticationFirebase.Repository.PhoneRepository

class PhoneViewModelFactory constructor(private val phoneRepository: PhoneRepository) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(PhoneViewModel::class.java)) {
            PhoneViewModel(this.phoneRepository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}