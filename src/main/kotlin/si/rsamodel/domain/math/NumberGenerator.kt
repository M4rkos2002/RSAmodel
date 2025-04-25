package si.rsamodel.domain.math

import java.math.BigInteger
import java.security.SecureRandom

fun generatePrimeByBits(bitLength: Int, random: SecureRandom = SecureRandom()): BigInteger {
    return BigInteger.probablePrime(bitLength, random)
}

fun areCoprime(a: BigInteger, b: BigInteger): Boolean {
    return a.gcd(b) == BigInteger.ONE
}

fun generatePrimeInRange(from: BigInteger, to: BigInteger, random: SecureRandom = SecureRandom()): BigInteger {
    require(from < to) { "'from' debe ser menor que 'to'" }

    val certainty = 100
    val range = to.subtract(from)
    var attempts = 0

    while (attempts < 10_000) {
        val candidate = from.add(BigInteger(range.bitLength(), random))

        if (candidate >= from && candidate <= to && candidate.isProbablePrime(certainty)) {
            return candidate
        }

        attempts++
    }

    throw IllegalStateException("No se encontró un número primo en el rango después de muchos intentos.")
}

//a×x+b×y=GCD(a,b)
fun generateEuclideanInverse(a: BigInteger, b: BigInteger): BigInteger {
    var oldR = a
    var r = b
    var oldS = BigInteger.ONE
    var s = BigInteger.ZERO
    var oldT = BigInteger.ZERO
    var t = BigInteger.ONE

    while (r != BigInteger.ZERO) {
        val quotient = oldR.divide(r)
        val tempR = r
        r = oldR.subtract(quotient.multiply(r))
        oldR = tempR

        val tempS = s
        s = oldS.subtract(quotient.multiply(s))
        oldS = tempS

        val tempT = t
        t = oldT.subtract(quotient.multiply(t))
        oldT = tempT
    }

    return oldS
}

fun stringToBigInteger(s: String): BigInteger {
    val bytes = s.toByteArray(Charsets.UTF_8)
    return BigInteger(1, bytes)
}

fun BigIntegerToString(number: BigInteger): String {
    val bytes = number.toByteArray()
    val cleanBytes = if (bytes.size > 1 && bytes[0] == 0.toByte()) bytes.drop(1).toByteArray() else bytes
    return String(cleanBytes, Charsets.UTF_8)
}
