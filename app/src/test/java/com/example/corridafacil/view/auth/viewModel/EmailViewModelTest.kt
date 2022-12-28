package com.example.corridafacil.view.auth.viewModel

import com.example.corridafacil.data.models.dao.PassageiroDAO
import com.example.corridafacil.data.repository.auth.email.EmailRepository
import com.example.corridafacil.data.repository.auth.email.EmailRepositoryImpl
import com.example.corridafacil.domain.services.AuthenticationFirebaseSevice.Email.AuthenticationEmailFirebaseServiceImpl
import com.example.corridafacil.domain.services.FirebaseCloudStorage.FirebaseStorageCloud
import io.mockk.mockk
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class EmailViewModelTest {

    @BeforeEach
    fun setUp() {
    }

    @Test
    fun verify_time_response_method_login_EmailViewModel(){
        val mockPassageiroDAO = mockk<PassageiroDAO>()
        val mockAuthenticationEmailFirebaseServiceImpl = mockk<AuthenticationEmailFirebaseServiceImpl>()
        val mockFirebaseStorageCloud = mockk<FirebaseStorageCloud>()
        val emailRepositoryImpl = EmailRepositoryImpl(mockPassageiroDAO,
                                                        mockAuthenticationEmailFirebaseServiceImpl,
                                                        mockFirebaseStorageCloud)

        emailRepositoryImpl.singEmailPassword("matheuszeramossilva2000@gmail.com","P@scoa2143")
    }

    @AfterEach
    fun tearDown() {
    }
}