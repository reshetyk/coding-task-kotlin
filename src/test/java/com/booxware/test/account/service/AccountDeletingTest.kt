package com.booxware.test.account.service

import com.booxware.test.account.exception.AccountServiceException
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.Test


class AccountDeletingTest : AccountAbstractTest() {

    @Test
    @Throws(Exception::class)
    fun shouldSuccessfulDeleteAccount() {
        val (id) = accountService.register("alex", "email", "1234")

        assertThat(id.isPresent).isTrue()
        accountService.deleteAccount("alex")
        val savedAccount = repository.findByName("alex")

        assertThat(savedAccount.isPresent).isFalse()
    }


    @Test
    @Throws(Exception::class)
    fun shouldThrowExceptionIfAccountNotFound() {
        accountService.register("user1", "user@gmail.com", "1234")
        accountService.register("user2", "user2@gmail.com", "1234")

        assertThatThrownBy { accountService.deleteAccount("user3") }
                .isInstanceOf(AccountServiceException::class.java)
                .hasMessageContaining("Not found user with name [user3]")
    }
}