package com.booxware.test.account.domain

import java.io.Serializable
import java.util.Arrays
import java.util.Date
import java.util.Optional

/**
 * The encryption can be very simple, we don't put much emphasis on the
 * encryption algorithm.
 */
data class Account internal constructor(val id: Optional<Long> = Optional.empty(),
                                        val username: String,
                                        val encryptedPassword: ByteArray,
                                        val email: String,
                                        val salt: String = "",
                                        val lastLogin: Date = Date()) : Serializable {

    constructor(id: Long,
                username: String,
                encryptedPassword: ByteArray,
                salt: String,
                email: String,
                lastLogin: Date)
            : this(Optional.of(id), username, encryptedPassword, salt, email, lastLogin)

    constructor(username: String, encryptedPassword: ByteArray, email: String) :
            this(Optional.empty(), username, encryptedPassword, email)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Account

        if (id != other.id) return false
        if (username != other.username) return false
        if (!Arrays.equals(encryptedPassword, other.encryptedPassword)) return false
        if (email != other.email) return false
        if (salt != other.salt) return false
        if (lastLogin != other.lastLogin) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + username.hashCode()
        result = 31 * result + Arrays.hashCode(encryptedPassword)
        result = 31 * result + email.hashCode()
        result = 31 * result + salt.hashCode()
        result = 31 * result + lastLogin.hashCode()
        return result
    }


}
