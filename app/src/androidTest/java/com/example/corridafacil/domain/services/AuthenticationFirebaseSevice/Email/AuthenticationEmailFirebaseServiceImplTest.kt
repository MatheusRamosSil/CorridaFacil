package com.example.corridafacil.domain.services.AuthenticationFirebaseSevice.Email

import com.example.corridafacil.data.models.Passageiro
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.BeforeEach
import org.mockito.Mockito

class AuthenticationEmailFirebaseServiceImplTest {

    @Before
    fun setUp() {
    }

    @Test
    fun test(){
        assertEquals(2,1+1)
    }

    @Test
    fun test_function_signInEmailPassword() = runBlocking{
        val emailFirebaseServiceImpl = MockAuthenticationEmailFirebaseServiceImpl()

        val result = emailFirebaseServiceImpl.singInEmailAndPassword("matheuszeramossilva2000@gmail.com","P@scoa2143")

        assertEquals("IA0ICvQNKpXvW3nitLm7BwzLYc23",result)
    }

    @Test
    fun fireabse_emailAndPassword(){
        val mock = mockk<FirebaseAuth>( relaxed = true)
        val result = mock.createUserWithEmailAndPassword("matheuszeramossilva2000@gmail.com","P@scoa2143").isSuccessful

        assertEquals("IA0ICvQNKpXvW3nitLm7BwzLYc23",result)

    }



    @After
    fun tearDown() {
    }

}

class MockAuthenticationEmailFirebaseServiceImpl():AuthenticathionEmail {

    private val instanceMockFireAuthentication = FirebaseAuth.getInstance()

    override suspend fun singInEmailAndPassword(emailUsuario: String, password: String): AuthResult? {
        instanceMockFireAuthentication.currentUser
        //return instanceMockFireAuthentication.signInWithEmailAndPassword(emailUsuario,password).await().user?.uid.toString()
        return instanceMockFireAuthentication.signInWithEmailAndPassword(emailUsuario,password).await()
    }

}

interface AuthenticathionEmail{

    suspend fun singInEmailAndPassword(emailUsuario: String, password: String): AuthResult?
}