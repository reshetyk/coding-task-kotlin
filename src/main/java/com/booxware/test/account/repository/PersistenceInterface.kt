package com.booxware.test.account.repository

import com.booxware.test.account.domain.Account

import java.util.Optional

/**
 * Persistence can be very simple, for example an in memory hash map.
 *
 */
interface PersistenceInterface {

    fun save(a: Account): Account

    fun findById(id: Long): Optional<Account>

    fun findByName(name: String): Optional<Account>

    fun delete(account: Account)

}
