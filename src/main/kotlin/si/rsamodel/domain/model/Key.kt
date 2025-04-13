package si.rsamodel.domain.model

import java.math.BigInteger

interface Key<P, S> {
    val publicKey: P
    val privateKey: S
}

data class RsaPublicKey(val modulus: BigInteger, val exponent: BigInteger)

data class RsaPrivateKey(val inverse: BigInteger)

data class RsaKey(
    override val publicKey: RsaPublicKey,
    override val privateKey: RsaPrivateKey,
) : Key<RsaPublicKey, RsaPrivateKey>