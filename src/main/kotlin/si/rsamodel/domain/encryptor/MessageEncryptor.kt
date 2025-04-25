package si.rsamodel.domain.encryptor

import java.math.BigInteger

interface MessageEncryptor<P> {

    fun encrypt(message: String, key: P): BigInteger

    fun decrypt(message: BigInteger, key: P): String
}