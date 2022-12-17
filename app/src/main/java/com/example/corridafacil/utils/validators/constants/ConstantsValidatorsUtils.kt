package com.example.corridafacil.utils.validators.constants


object ConstantsValidatorsUtils {

    const val  FORMAT_CODE_SMS = "(|^)\\d{4,6}"

    const val  FORMAT_NAME = "^[a-zA-z].{4,20}$"

    const val  FORMAT_PHONE = ("^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{5}[- .]?\\d{4}$"
            + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?){2}\\d{3}$"
            + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?)(\\d{2}[ ]?){2}\\d{2}$")

    const val  FORMAT_PASSWORD = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$"

}
