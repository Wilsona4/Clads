package com.decagonhq.clads.util

import com.decagonhq.clads.util.ValidationObject.validateEmail
import com.decagonhq.clads.util.ValidationObject.validatePasswordMismatch
import junit.framework.TestCase
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ValidationObjectTest {

    /*Set Up Mock Input Data*/
    private val emailTrue: String = "johndoe@gmail.com"
    private val emailFalse: String = "johndoe@com"

    private val password: String = "Myfa4ourite@Pass"
    private val confirmPasswordTrue: String = "Myfa4ourite@Pass"
    private val confirmPasswordFalse: String = "Myfa4our@Pass"

    /*Test Validate Email Function with Correct Mail*/
    @Test
    fun test_validateEmail_isTrue() {
        val result = validateEmail(emailTrue)
        TestCase.assertTrue(result)
    }

    /*Test Validate Email Function with Wrong Email*/
    @Test
    fun test_validateEmail_isFalse() {
        val result = validateEmail(emailFalse)
        TestCase.assertFalse(result)
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
}
