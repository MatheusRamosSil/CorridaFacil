package com.example.corridafacil.dependicyInjection


import com.example.corridafacil.data.models.dao.PassageiroDAO
import com.example.corridafacil.data.models.dao.PassageiroDAOImpl
import com.example.corridafacil.data.repository.auth.email.EmailRepository
import com.example.corridafacil.data.repository.auth.email.EmailRepositoryImpl
import com.example.corridafacil.view.auth.viewModel.AuthenticathionEmail
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
interface DataModule {


    @Binds
    fun bindEmailRepository(
        emailRepositoryImpl: EmailRepositoryImpl
    ): EmailRepository

    @Binds
    fun bindCreatePassageiroDAO(
        passageiroDAO: PassageiroDAOImpl
    ):PassageiroDAO

}