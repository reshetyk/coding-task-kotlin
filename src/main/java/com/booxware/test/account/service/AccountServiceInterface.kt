package com.booxware.test.account.service

import com.booxware.test.account.domain.Account

import java.util.Date

/**
 * Service for account management.
 */
interface AccountServiceInterface {

    /**
     * Logs in the user, if the username exists and the password is correct.
     * Updates the last login date
     *
     * @param username the User's name
     * @param password the clear text password
     * @return the logged in account
     * @throws com.booxware.test.account.exception.AccountServiceException if any errors occur
     */
    fun login(username: String, password: String): Account

    /**
     * Registers a new Account, if the username doesn't exist yet and logs in
     * the user.
     *
     * @param username the User's name
     * @param email    the email address of the user
     * @param password the clear text password
     * @return the newly registered Account
     * @throws com.booxware.test.account.exception.AccountServiceException if any errors occur
     */
    fun register(username: String, email: String, password: String): Account

    /**
     * Deletes an Account, if the user exist.
     *
     * @param username the User's name
     * @throws com.booxware.test.account.exception.AccountServiceException if any errors occur
     */
    fun deleteAccount(username: String)

    /**
     * Checks if a user has logged in since a provided timestamp.
     *
     * @param date
     * @return true if the user has logged in since the provided timestamp, else
     * false.
     * @throws com.booxware.test.account.exception.AccountServiceException if any error occurs
     */
    fun hasLoggedInSince(date: Date, username: String): Boolean
}
