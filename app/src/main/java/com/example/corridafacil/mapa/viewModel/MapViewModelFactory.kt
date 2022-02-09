package com.example.corridafacil.mapa.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.corridafacil.mapa.repository.MapRepository

class MapViewModelFactory (private val mapaRepository: MapRepository): ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MapsViewModel::class.java)) {
            MapsViewModel(this.mapaRepository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}


