package com.example.corridafacil.utils.validators

import android.util.Patterns
import com.example.corridafacil.utils.validators.constants.ConstantsValidatorsUtils
import java.util.regex.Pattern

object ValidatorsFieldsForms {

    fun isValidPhoneNumber(numberPhone:String? ): Boolean{
        return Pattern.matches(ConstantsValidatorsUtils.FORMAT_PHONE,numberPhone)
    }

    fun isValidEmail(email: String?): Boolean {
        return Pattern.matches(Patterns.EMAIL_ADDRESS.toString(),email)
    }

    fun isValidPassword(password: String?): Boolean {
        return Pattern.matches(ConstantsValidatorsUtils.FORMAT_PASSWORD,password)
    }

    fun isValidFormatName(name: String?): Boolean {
        return Pattern.matches(ConstantsValidatorsUtils.FORMAT_NAME,name)
    }

    fun isValidCodeSMS(codeSMS: String?): Boolean {
        return Pattern.matches(ConstantsValidatorsUtils.FORMAT_CODE_SMS,codeSMS)
    }

}