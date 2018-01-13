package com.booxware.test.account.repository

import com.booxware.test.account.domain.Account
import java.util.*
import java.util.Objects.isNull
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicLong

class PersistenceInMemoryRepository : PersistenceInterface {

    private val storage = ConcurrentHashMap<Long, Account>()

    val lastId: AtomicLong
        @Synchronized get() = AtomicLong(storage.keys.stream()
                .sorted { o1, o2 -> (o2 - o1).toInt() }
                .findFirst()
                .orElseGet { INITIAL_ID })

    @Synchronized override fun save(a: Account): Account {
        val id = a.id.orElseGet { lastId.incrementAndGet() }
        val account = Account(id, a.username, a.encryptedPassword, a.salt, a.email, a.lastLogin)
        storage.put(id, account)
        return account
    }

    @Synchronized override fun findById(id: Long): Optional<Account> {
        val account = storage[id]
        return if (isNull(account)) Optional.empty() else Optional.of(account!!)
    }

    @Synchronized override fun findByName(name: String): Optional<Account> {
        assertArgumentNotNull(name)

        val accounts = storage.values.filter { (_, username) -> name == username }

        assertThatNotMoreThanOne(accounts)

        return accounts.stream().findFirst()
    }

    @Synchronized override fun delete(account: Account) {
        assertArgumentNotNull(account)

        if (account.id.isPresent && storage.containsKey(account.id.get())) {
            storage.remove(account.id.get())
        } else {
            storage.remove(find(account).id.get())
        }
    }

    @Synchronized private fun find(account: Account): Account {
        val accounts = storage.values.filter { a -> account == a }

        assertThatNotMoreThanOne(accounts)

        return accounts.first()
    }

    internal fun countAll(): Int? {
        return storage.size
    }

    companion object {

        private val INITIAL_ID = 0L

        private fun assertArgumentNotNull(obj: Any) {
            if (Objects.isNull(obj))
                throw IllegalArgumentException("argument cannot be null")
        }

        private fun assertThatNotMoreThanOne(accounts: List<Account>) {
            if (accounts.size > 1)
                throw IllegalStateException("found more than one representation of [" + Account::class.java.toString() + "]")
        }
    }
}
