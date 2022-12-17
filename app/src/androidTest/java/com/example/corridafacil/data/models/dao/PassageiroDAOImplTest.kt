package com.example.corridafacil.data.models.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.corridafacil.data.models.Passageiro
import kotlinx.coroutines.*
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PassageiroDAOImplTest{

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun readDataUser()= runTest {
        val passageiroDAOImpl = PassageiroDAOImpl()
        val uid = "Aswerwer@34234"

        val result = passageiroDAOImpl.readDataUser(uid)

        assertEquals(uid, result.id)
    }

    @Test
    fun createNewUser() = runTest {
        val passageiro = Passageiro("Aswerwer@34234",
                                 "Matheus",
                             "Ramos",
                                 "email@email.com",
                                "4545645645664",
                          null,
                        null,
                                    null,
                                  true,
                            "AWE@ERSESR4345445")

        val passageiroDAOImpl = PassageiroDAOImpl()

        val result = passageiroDAOImpl.createNewPassageiro(passageiro)

        assertEquals(true,result)


    }
}