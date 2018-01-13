package com.booxware.test.account.service

import com.booxware.test.account.exception.AccountServiceException
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.Test
import java.util.*

class AccountLoginTest : AccountAbstractTest() {

    @Test
    @Throws(Exception::class)
    fun shouldUpdateLastLoginDateIfLoginIsSuccessful() {
        val dateBefore = Date()
        accountService.register("alex", "email", "1234")
        val (id, _, _, _, _, lastLogin) = accountService.login("alex", "1234")
        val dateAfter = Date()

        assertThat(id.isPresent).isTrue()
        assertThat(lastLogin).isBetween(dateBefore, dateAfter, true, true)
    }

    @Test
    @Throws(Exception::class)
    fun shouldThrowExceptionIfPasswordIncorrect() {
        accountService.register("user1", "user@gmail.com", "1234")
        accountService.register("user2", "user2@gmail.com", "12345")

        assertThatThrownBy { accountService.login("user1", "1111") }
                .isInstanceOf(AccountServiceException::class.java)
                .hasMessageContaining("Cannot login. Password is incorrect")
    }

    @Test
    @Throws(Exception::class)
    fun shouldThrowExceptionIfUserNotRegistered() {
        accountService.register("user2", "user2@gmail.com", "12345")

        assertThatThrownBy { accountService.login("user1", "1111") }
                .isInstanceOf(AccountServiceException::class.java)
                .hasMessageContaining("Not found user with name [user1]")
    }
}