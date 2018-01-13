package com.booxware.test.account.repository

import com.booxware.test.account.domain.Account
import org.junit.Before
import org.junit.Test

import java.util.Date

import org.assertj.core.api.Assertions.assertThat

class PersistenceInMemoryRepositoryTest {
    private lateinit var repository: PersistenceInMemoryRepository

    @Before
    @Throws(Exception::class)
    fun setUp() {
        repository = PersistenceInMemoryRepository()
    }

    @Test
    @Throws(Exception::class)
    fun shouldGenerateUniqueId() {
        repository.save(Account("user1", "111".toByteArray(), "email1"))
        assertThat(repository.lastId.get()).isEqualTo(1)
        assertThat(repository.lastId.get()).isEqualTo(1)

        repository.save(Account("user2", "222".toByteArray(), "email2"))
        assertThat(repository.lastId.get()).isEqualTo(2)
        assertThat(repository.lastId.get()).isEqualTo(2)

        repository.save(Account("user3", "333".toByteArray(), "email3"))
        assertThat(repository.lastId.get()).isEqualTo(3)
        assertThat(repository.lastId.get()).isEqualTo(3)
    }

    @Test
    @Throws(Exception::class)
    fun shouldAddNewIfAccountIdEmpty() {
        var account1 = Account("user1", "111".toByteArray(), "email1")
        assertThat(account1.id.isPresent).isFalse()
        account1 = repository.save(account1)
        assertThat(account1.id.isPresent).isTrue()
        assertThat(repository.countAll()).isEqualTo(1)

        var account2 = Account("user2", "111".toByteArray(), "email2")
        assertThat(account2.id.isPresent).isFalse()
        account2 = repository.save(account2)
        assertThat(account2.id.isPresent).isTrue()
        assertThat(repository.countAll()).isEqualTo(2)
    }


    @Test
    @Throws(Exception::class)
    fun shouldUpdateExistedAccountIfIdNotEmpty() {
        var accountWithoutId = Account("user1", "111".toByteArray(), "email1")
        assertThat(accountWithoutId.id.isPresent).isFalse()
        accountWithoutId = repository.save(accountWithoutId)
        assertThat(accountWithoutId.id.isPresent).isTrue()
        assertThat(repository.countAll()).isEqualTo(1)

        var accountWithId = Account(1L, "user", "222".toByteArray(), "salt", "email1", Date())
        assertThat(accountWithId.id.isPresent).isTrue()
        accountWithId = repository.save(accountWithId)
        assertThat(accountWithId.id.isPresent).isTrue()
        assertThat(repository.countAll()).isEqualTo(1)
    }


}