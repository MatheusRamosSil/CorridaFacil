package com.example.corridafacil.Email

import com.example.corridafacil.domain.services.AuthenticationFirebaseSevice.Email.AuthenticationEmailFirebaseServiceImpl
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.spy
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class AuthenticationEmailFirebaseServiceImplTest {

    @Before
    fun setUp() {
    }

    @Test
    fun test_function_signInEmailPassword() = runBlocking {

        val authenticationEmailFirebaseServiceImpl = AuthenticationEmailFirebaseServiceImpl()
       // val spy = spy(authenticationEmailFirebaseServiceImpl)

        val result = authenticationEmailFirebaseServiceImpl
                    .singInEmailAndPassword("matheuszeramossilva2000@gmail.com","P@scoa2143")

        assertEquals("oi", result)

    }

    @After
    fun tearDown() {
    }
}