package com.example.corridafacil.domain.services.AuthenticationFirebaseSevice.Email

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import org.junit.Assert
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.mockito.Mockito.*

class AuthenticationEmailFirebaseServiceImplTest {

    @BeforeEach
    fun setUp() {
    }
    @Test
    fun test(){
        assertEquals(2,1+1)
    }
    @Test
    fun test_function_signInEmailPassword2() = runBlocking{

        val mockFireAuthentication = mock(Firebase::class.java)
        val instanceMockFireAuthentication = mockFireAuthentication.auth

        val emailFirebaseServiceImpl = MockAuthenticationEmailFirebaseServiceImpl()

        val result = emailFirebaseServiceImpl.singInEmailAndPassword("matheuszeramossilva2000@gmail.com","P@scoa2143")

        assertEquals("Oi",result)
    }

    @Test
    fun test_function_signInEmailPassword() = runBlocking{
        val instanceMockFireAuthentication = mockkStatic(FirebaseAuth::class)
        every { FirebaseAuth.getInstance() } returns mockk(relaxed = true)


        val emailFirebaseServiceImpl = MockAuthenticationEmailFirebaseServiceImpl()

        val result = emailFirebaseServiceImpl.singInEmailAndPassword("matheuszeramossilva2000@gmail.com","P@scoa2143")

        Assert.assertEquals("IA0ICvQNKpXvW3nitLm7BwzLYc23", result)
    }
    @AfterEach
    fun tearDown() {
    }
}

class MockAuthenticationEmailFirebaseServiceImpl() {
    private val instanceMockFireAuthentication = FirebaseAuth.getInstance()

    suspend fun singInEmailAndPassword(emailUsuario: String, password: String): String {
        return instanceMockFireAuthentication.signInWithEmailAndPassword(emailUsuario,password).await().user?.uid.toString()
    }

}