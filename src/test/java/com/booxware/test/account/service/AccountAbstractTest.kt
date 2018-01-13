package com.booxware.test.account.service

import com.booxware.test.account.repository.PersistenceInMemoryRepository
import org.junit.Before

import java.security.MessageDigest

abstract class AccountAbstractTest {
    protected lateinit var accountService: AccountServiceInterface
    protected lateinit var repository: PersistenceInMemoryRepository

    @Before
    @Throws(Exception::class)
    fun setUp() {
        repository = PersistenceInMemoryRepository()
        accountService = AccountService(repository, MessageDigest.getInstance("MD5"))
    }
}
