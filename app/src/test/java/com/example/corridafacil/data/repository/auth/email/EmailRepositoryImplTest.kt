package com.example.corridafacil.data.repository.auth.email

import com.example.corridafacil.data.models.dao.PassageiroDAO
import com.example.corridafacil.domain.services.AuthenticationFirebaseSevice.Email.AuthenticationEmailFirebaseServiceImpl
import com.example.corridafacil.domain.services.FirebaseCloudStorage.FirebaseStorageCloud
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import com.example.corridafacil.view.auth.viewModel.Result.Success

class EmailRepositoryImplTest {

    @BeforeEach
    fun setUp() {
    }
    @Test
    fun verify_signInEmailPassword() = runBlocking{
        val mockPassageiroDAO =  mockk<PassageiroDAO>(relaxed = true)
        val mockAuthenticationEmailFirebaseServiceImpl = mockk<AuthenticationEmailFirebaseServiceImpl>(relaxed = true)
        val mockFirebaseCloud = mockk<FirebaseStorageCloud>(relaxed = true)

        val emailRepository = EmailRepositoryImpl(mockPassageiroDAO,
                                                    mockAuthenticationEmailFirebaseServiceImpl,
                                                    mockFirebaseCloud)

        val result = emailRepository.singEmailPassword("matheuszeramossilva2000@gmail,com","P@scoa2143")

        assertEquals(Success(true), result)
    }

    @Test
    fun verify_signInEmailPassword_failure() = runBlocking{
        val mockPassageiroDAO =  mockk<PassageiroDAO>(relaxed = true)
        val mockAuthenticationEmailFirebaseServiceImpl = mockk<AuthenticationEmailFirebaseServiceImpl>(relaxed = true)
        val mockFirebaseCloud = mockk<FirebaseStorageCloud>(relaxed = true)

        val emailRepository = EmailRepositoryImpl(mockPassageiroDAO,
            mockAuthenticationEmailFirebaseServiceImpl,
            mockFirebaseCloud)

        val result = emailRepository.singEmailPassword("matheujramos2000@gmail.com","ma@theus34")

        assertEquals(Success(true), result)
    }
    @AfterEach
    fun tearDown() {
    }
}