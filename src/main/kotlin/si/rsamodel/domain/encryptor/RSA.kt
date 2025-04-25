package si.rsamodel.domain.encryptor

import si.rsamodel.domain.math.BigIntegerToString
import si.rsamodel.domain.math.stringToBigInteger
import si.rsamodel.domain.model.RsaKey
import java.math.BigInteger

class RSA : MessageEncryptor<RsaKey> {

    override fun encrypt(message: String, key: RsaKey): BigInteger {
        if (message.isEmpty()) {
            throw IllegalArgumentException("El mensaje no puede estar vacío.")
        }

        val m = key.publicKey.modulus
        val e = key.publicKey.exponent

        val numericMessage = stringToBigInteger(message)
        val messageMod = numericMessage.mod(m) //[0, m - 1]
        return messageMod.modPow(e, m)
    }

    override fun decrypt(message: BigInteger, key: RsaKey): String {
        val inverse = key.privateKey.inverse
        val modulus = key.publicKey.modulus

        val numericMessage = message.modPow(inverse, modulus)
        return BigIntegerToString(numericMessage)
    }
}
