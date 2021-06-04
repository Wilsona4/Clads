package com.decagonhq.clads.util

<<<<<<< HEAD
import com.decagonhq.clads.util.ValidationObject.validateEmail
import com.decagonhq.clads.util.ValidationObject.validatePasswordMismatch
import junit.framework.TestCase
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
=======
import com.decagonhq.clads.util.ValidationObject.validateAccountCategory
import com.decagonhq.clads.util.ValidationObject.validateEmail
import com.decagonhq.clads.util.ValidationObject.validatePasswordMismatch
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
>>>>>>> eff8365b10d7154826f515049d49d0537db31269
import org.junit.Test

class ValidationObjectTest {

    /*Set Up Mock Input Data*/
    private val emailTrue: String = "johndoe@gmail.com"
    private val emailFalse: String = "johndoe@com"

    private val password: String = "Myfa4ourite@Pass"
    private val confirmPasswordTrue: String = "Myfa4ourite@Pass"
    private val confirmPasswordFalse: String = "Myfa4our@Pass"

<<<<<<< HEAD
=======
    private val accountCategory: String = "Tailor"

>>>>>>> eff8365b10d7154826f515049d49d0537db31269
    /*Test Validate Email Function with Correct Mail*/
    @Test
    fun test_validateEmail_isTrue() {
        val result = validateEmail(emailTrue)
<<<<<<< HEAD
        TestCase.assertTrue(result)
=======
        assertTrue(result)
>>>>>>> eff8365b10d7154826f515049d49d0537db31269
    }

    /*Test Validate Email Function with Wrong Email*/
    @Test
    fun test_validateEmail_isFalse() {
        val result = validateEmail(emailFalse)
<<<<<<< HEAD
        TestCase.assertFalse(result)
=======
        assertFalse(result)
>>>>>>> eff8365b10d7154826f515049d49d0537db31269
    }

    /*Test Validate Password Mismatch Function with Correct Passwords*/
    @Test
    fun test_passwordMismatch_isTrue() {
        val result = validatePasswordMismatch(password, confirmPasswordTrue)
        assertTrue(result)
    }
<<<<<<< HEAD
=======

>>>>>>> eff8365b10d7154826f515049d49d0537db31269
    /*Test Validate Password Mismatch Function with wrong Passwords*/
    @Test
    fun test_passwordMismatch_isFalse() {
        val result = validatePasswordMismatch(password, confirmPasswordFalse)
        assertFalse(result)
    }
<<<<<<< HEAD
=======

    /*Test Validate Account Type Function*/
    @Test
    fun test_accountCategory_isTrue() {
        val result = validateAccountCategory(accountCategory)
        assertTrue(result)
    }
>>>>>>> eff8365b10d7154826f515049d49d0537db31269
}
