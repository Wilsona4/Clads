package com.decagonhq.clads.util

import java.util.Locale

object ValidationObject {

    /*Function to validate Email*/
    fun validateEmail(email: String): Boolean {
        val pattern = "\\w+@[a-zA-Z_]+?\\.[a-zA-Z]{2,6}".toRegex()
        return email.matches(pattern)
    }

    /*Function to check password mismatch*/
    fun validatePasswordMismatch(password: String, confirmPassword: String): Boolean {
        return password == confirmPassword
    }

    /*Function to Validate Account Category Selection*/
    fun validateAccountCategory(account_Category: String): Boolean {
        return when (account_Category.toUpperCase(Locale.ROOT)) {
            AccountCategory.CLIENT.name -> true
            AccountCategory.WEAVER.name -> true
            AccountCategory.TAILOR.name -> true
            else -> false
        }
    }
}
