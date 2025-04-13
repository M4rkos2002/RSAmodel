package si.rsamodel.domain.key

import si.rsamodel.domain.math.*
import si.rsamodel.domain.model.Key
import si.rsamodel.domain.model.RsaKey
import si.rsamodel.domain.model.RsaPrivateKey
import si.rsamodel.domain.model.RsaPublicKey
import java.math.BigInteger

class RSAKeyGenerator (
    private val primeSize: Int
) : KeyGenerator<RsaPublicKey, RsaPrivateKey> {

    override fun generateKey(): Key<RsaPublicKey, RsaPrivateKey>{
        val p = generatePrimeByBits(this.primeSize)
        val q = generatePrimeByBits(this.primeSize)

        val n = p.multiply(q)

        val phi = this.calculatePhi(p,q)
        val e = this.generateExponent(phi)
        val d = this.calculateModInverse(e, phi)

        return RsaKey(
            RsaPublicKey(n, e),
            RsaPrivateKey(d)
        )
    }

    private fun generateExponent(phi: BigInteger): BigInteger {
        val e = generatePrimeInRange(BigInteger("1"), phi) //genera valor 1 < e < phi
        if (areCoprime(e, phi)) {
            return e
        } else {
            throw Exception("Invalid key: values are not coprime")
        }
    }


    private fun calculatePhi(p: BigInteger, q: BigInteger): BigInteger {
        return (p.subtract(BigInteger.ONE))
            .multiply(q.subtract(BigInteger.ONE))
    }

    private fun calculateModInverse(e: BigInteger, phi: BigInteger): BigInteger {
        val d = generateEuclideanInverse(e, phi)
        return when {
            d < BigInteger.ZERO -> d + phi  // Si la inversa es negativa
            d >= phi -> d - phi             // Si d es mayor que phi
            else -> d                       // En cualquier otro caso, lo dejamos igual
        }
    }

}