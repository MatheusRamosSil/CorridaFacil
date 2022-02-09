package com.example.corridafacil.models

data class Passageiro (var id: String? = null,
                  var nome: String? = null,
                  var sobrenome: String? = null,
                  var email: String? = null,
                  var telefone: String? = null,
                  var senha: String? = null,
                  var foto: String? = null,
                  var ativado: Boolean? = null,
                  var tokenPassageiro: String? = null) {

    companion object Factory{
        fun create():Passageiro = Passageiro()
    }


}