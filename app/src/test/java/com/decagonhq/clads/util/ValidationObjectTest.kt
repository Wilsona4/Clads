package com.decagonhq.clads.util

import com.decagonhq.clads.util.ValidationObject.validateAccountCategory
import com.decagonhq.clads.util.ValidationObject.validateEmail
import com.decagonhq.clads.util.ValidationObject.validatePasswordMismatch
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Test

class ValidationObjectTest {

    /*Set Up Mock Input Data*/
    private val emailTrue: String = "johndoe@gmail.com"
    private val emailFalse: String = "johndoe@com"

    private val password: String = "Myfa4ourite@Pass"
    private val confirmPasswordTrue: String = "Myfa4ourite@Pass"
    private val confirmPasswordFalse: String = "Myfa4our@Pass"

    private val accountCategory: String = "Tailor"

    /*Test Validate Email Function with Correct Mail*/
    @Test
    fun test_validateEmail_isTrue() {
        val result = validateEmail(emailTrue)
        assertTrue(result)
    }

    /*Test Validate Email Function with Wrong Email*/
    @Test
    fun test_validateEmail_isFalse() {
        val result = validateEmail(emailFalse)
        assertFalse(result)
    }

    /*Test Validate Password Mismatch Function with Correct Passwords*/
    @Test
    fun test_passwordMismatch_isTrue() {
        val result = validatePasswordMismatch(password, confirmPasswordTrue)
        assertTrue(result)
    }

    /*Test Validate Password Mismatch Function with wrong Passwords*/
    @Test
    fun test_passwordMismatch_isFalse() {
        val result = validatePasswordMismatch(password, confirmPasswordFalse)
        assertFalse(result)
    }

    /*Test Validate Account Type Function*/
    @Test
    fun test_accountCategory_isTrue() {
        val result = validateAccountCategory(accountCategory)
        assertTrue(result)
    }
}
