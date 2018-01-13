package com.booxware.test.account.service

import com.booxware.test.account.exception.AccountServiceException
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.Test


class AccountRegistrationTest : AccountAbstractTest() {

    @Test
    @Throws(Exception::class)
    fun successfulRegistration() {
        val account = accountService.register("alex", "email", "1234")

        assertThat(account.id.isPresent).isTrue()
        val savedAccount = repository.findById(account.id.get())

        assertThat(savedAccount.isPresent).isTrue()
        assertThat(savedAccount.get()).isEqualTo(account)
    }

    @Test
    @Throws(Exception::class)
    fun shouldThrowExceptionWhenUserAlreadyExists() {
        accountService.register("user1", "user@gmail.com", "1234")
        accountService.register("user2", "user2@gmail.com", "1234")

        assertThatThrownBy { accountService.register("user1", "user@gmail.com", "1234") }
                .isInstanceOf(AccountServiceException::class.java)
                .hasMessageContaining("User with name [user1] already exists")
    }
}