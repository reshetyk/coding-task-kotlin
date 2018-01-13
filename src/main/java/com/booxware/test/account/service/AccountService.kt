package com.booxware.test.account.service

import com.booxware.test.account.domain.Account
import com.booxware.test.account.exception.AccountServiceException
import com.booxware.test.account.repository.PersistenceInterface

import java.security.MessageDigest
import java.util.Date
import java.util.Objects

class AccountService(private val repository: PersistenceInterface,
                     private val messageDigest: MessageDigest) : AccountServiceInterface {

    override fun login(username: String, password: String): Account {
        try {
            val (id, username1, encryptedPassword, email, salt) = findAccountByUsername(username)

            if (Objects.deepEquals(encryptedPassword, encryptPassword(password))) {
                return repository.save(Account(id.get(), username1, encryptedPassword, salt, email, Date()))
            }

        } catch (ex: Exception) {
            throw AccountServiceException("Error is occurred during login: " + ex.message, ex)
        }

        throw AccountServiceException("Cannot login. Password is incorrect")
    }

    override fun register(username: String, email: String, password: String): Account {
        return try {
            val account = Account(username, encryptPassword(password), email)

            if (repository.findByName(account.username).isPresent)
                throw RuntimeException("User with name [$username] already exists")

            repository.save(account)

        } catch (ex: Exception) {
            throw AccountServiceException("Error is occurred during registration: " + ex.message, ex)
        }

    }

    override fun deleteAccount(username: String) {
        try {
            repository.delete(findAccountByUsername(username))
        } catch (ex: Exception) {
            throw AccountServiceException("Error is occurred during account deleting: " + ex.message, ex)
        }

    }

    override fun hasLoggedInSince(date: Date, username: String): Boolean {
        return try {
            val (_, _, _, _, _, lastLogin) = findAccountByUsername(username)
            date == lastLogin
        } catch (ex: Exception) {
            throw AccountServiceException("Error is occurred during checking has user logged in since ["
                    + date.toString() + "]:" + ex.message, ex)
        }

    }

    private fun findAccountByUsername(username: String): Account {
        return repository.findByName(username).orElseThrow { AccountServiceException("Not found user with name [$username]") }
    }

    private fun encryptPassword(password: String): ByteArray {
        return messageDigest.digest(password.toByteArray())
    }
}
