package si.rsamodel.domain.encryptor

interface MessageEncryptor {

    public fun encrypt(message: String): String

    public fun decrypt(message: String): String
}