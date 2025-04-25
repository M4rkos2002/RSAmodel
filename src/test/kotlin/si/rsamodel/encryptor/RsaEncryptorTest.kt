package si.rsamodel.encryptor

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import si.rsamodel.domain.encryptor.RSA
import si.rsamodel.domain.key.RSAKeyGenerator
import si.rsamodel.domain.model.RsaKey

class RsaEncryptorTest {

    private val rsa = RSA()

    private val key = RSAKeyGenerator(1024).generateKey() as RsaKey

    @Test
    fun `encrypt and decrypt returns original message`() {
        val originalMessage = "Hola"
        val encrypted = rsa.encrypt(originalMessage, key)
        val decrypted = rsa.decrypt(encrypted, key)

        assertEquals(originalMessage, decrypted)
    }

    @Test
    fun `encrypting same message returns same ciphertext`() {
        val message = "Kotlin"
        val encrypted1 = rsa.encrypt(message, key)
        val encrypted2 = rsa.encrypt(message, key)

        assertEquals(encrypted1, encrypted2)
    }

    @Test
    fun `different messages produce different ciphertexts`() {
        val message1 = "Hola"
        val message2 = "Chau"

        val encrypted1 = rsa.encrypt(message1, key)
        val encrypted2 = rsa.encrypt(message2, key)

        assertNotEquals(encrypted1, encrypted2)
    }

    @Test
    fun `empty string encrypt and decrypt`() {
        val message = ""

        assertThrows(IllegalArgumentException::class.java) {
            rsa.encrypt(message, key)
        }
    }

    @Test
    fun `non-ascii characters encrypt and decrypt`() {
        val message = "こんにちは" // Japonés: "Hola"
        val encrypted = rsa.encrypt(message, key)
        val decrypted = rsa.decrypt(encrypted, key)

        assertEquals(message, decrypted)
    }

}