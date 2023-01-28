package com.example.corridafacil.dependicyInjection


import com.example.corridafacil.authenticationFirebase.repository.PhoneRepositoryImpl
import com.example.corridafacil.data.repository.auth.AuthEmailRepository
import com.example.corridafacil.data.repository.auth.AuthEmailRepositoryImplementation
import com.example.corridafacil.data.repository.auth.phone.PhoneRepository
import com.example.corridafacil.domain.services.AuthenticationFirebaseSevice.AuthenticathionEmail
import com.example.corridafacil.domain.services.AuthenticationFirebaseSevice.AuthenticationFirebaseService
import com.example.corridafacil.domain.services.AuthenticationFirebaseSevice.Phone.AuthPhoneFirebaseService
import com.example.corridafacil.domain.services.AuthenticationFirebaseSevice.Phone.AuthPhoneImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)
abstract class DataModule {

    @Binds
     abstract fun bindEmailRepository(
       authEmailRepositoryImplementation: AuthEmailRepositoryImplementation
    ): AuthEmailRepository

    @Binds
    abstract fun bindPhoneRepository(
       phoneRepositoryImpl: PhoneRepositoryImpl
    ): PhoneRepository


    @Binds
    abstract fun bindAuthenticationEmail(
        authenticationFirebaseService: AuthenticationFirebaseService
    ):AuthenticathionEmail

    @Binds
    abstract fun bindAuthPhone(
        authPhoneFirebaseService: AuthPhoneFirebaseService
    ):AuthPhoneImpl


}