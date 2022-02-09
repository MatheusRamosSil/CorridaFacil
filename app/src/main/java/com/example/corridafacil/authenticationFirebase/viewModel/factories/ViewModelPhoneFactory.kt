package com.example.corridafacil.authenticationFirebase.viewModel.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.corridafacil.authenticationFirebase.repository.phone.PhoneRepository
import com.example.corridafacil.authenticationFirebase.viewModel.PhoneViewModel

class ViewModelPhoneFactory constructor(private val phoneRepository: PhoneRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(PhoneViewModel::class.java)) {
            PhoneViewModel(phoneRepository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}