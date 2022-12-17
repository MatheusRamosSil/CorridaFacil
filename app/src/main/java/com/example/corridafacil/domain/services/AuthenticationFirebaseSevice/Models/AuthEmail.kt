package com.example.corridafacil.domain.services.AuthenticationFirebaseSevice.Models

class AuthEmail {
    lateinit var email:String
    lateinit var password:String

    companion object Factory{
        fun create(): AuthEmail = AuthEmail()
    }

}