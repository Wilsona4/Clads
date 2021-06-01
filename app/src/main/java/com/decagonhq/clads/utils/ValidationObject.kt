package com.decagonhq.clads.utils

import java.util.regex.Pattern

object ValidationObject {
    //Email validator
/*Function to validate Email*/
    fun validateEmail(email: String): Boolean {
        val pattern = "\\w+@[a-zA-Z_]+?\\.[a-zA-Z]{2,6}".toRegex()
        return email.matches(pattern)
    }
}