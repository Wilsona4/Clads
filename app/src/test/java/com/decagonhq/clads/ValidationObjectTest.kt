//package com.decagonhq.clads
//
//import com.decagonhq.clads.utils.ValidationObject.validateEmail
//import org.junit.Assert
//import org.junit.Test
//
//class ValidationObjectTest {
//    @Test
//    fun validate_email_one() {
//        val correctEmail = "femiogundipe@gmail.com"
//        val validator = validateEmail(correctEmail)
//        Assert.assertEquals(validator, true)
//    }
//    // Testing the method with incorrect email
//    @Test
//    fun validate_incorrect_email_two() {
//        val invalidEmail = "femiogundipe@gmail."
//        val validator = validateEmail(invalidEmail)
//        Assert.assertEquals(validator, false)
//    }
//    // Testing the method with incorrect email
//    @Test
//    fun validate_incorrect_email_three() {
//        val invalidEmail1 = "femiogundipe@gmailcom"
//        val validator = validateEmail(invalidEmail1)
//        Assert.assertEquals(validator, false)
//    }
//    // Testing the method with incorrect email
//    @Test
//    fun validate_email_four() {
//        val invalidEmail2 = "femiogundipe@gmailcom"
//        val validator = validateEmail(invalidEmail2)
//        Assert.assertEquals(validator, false)
//    }
//    // Testing the method with incorrect email
//    @Test
//    fun validate_incorrect_email_five() {
//        val invalidEmail3 = "femiogundipe01gmail.com"
//        val validator = validateEmail(invalidEmail3)
//        Assert.assertFalse(validator)
//    }
//    // Testing the method with incorrect email
//    @Test
//    fun validate_incorrect_email_sive() {
//        val invalidEmail4 = "femiogundipe-gmail.com"
//        val validator = validateEmail(invalidEmail4)
//        Assert.assertFalse(validator)
//    }
//}
