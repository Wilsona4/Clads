package com.decagonhq.clads.ui.authentication

import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
    SignUpOptionsFragmentTest::class,
    EmailSignUpFragmentTest::class,
    EmailConfirmationFragmentTest::class,
    LoginFragmentTest::class,
    ForgotPasswordFragmentTest::class,
    ResetPasswordFragmentTest::class,
    ResetPasswordConfirmationFragmentTest::class
)
class AuthenticationSuite
