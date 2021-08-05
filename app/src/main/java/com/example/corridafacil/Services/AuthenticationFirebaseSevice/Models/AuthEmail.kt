package com.example.corridafacil.Services.AuthenticationFirebaseSevice.Models

class AuthEmail {
    lateinit var email:String
    lateinit var password:String

    companion object Factory{
        fun create(): AuthEmail = AuthEmail()
    }

}