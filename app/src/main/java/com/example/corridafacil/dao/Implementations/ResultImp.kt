package com.example.corridafacil.dao.Implementations

import com.google.firebase.database.DatabaseException

interface ResultImp {
    fun onSuccess(post: String)
    fun onFailure(mensage: DatabaseException)

}