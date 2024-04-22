package rationals

import java.math.BigInteger


fun main() {
    val half = 1 divBy 2
    val third = 1 divBy 3

    val sum: Rational = half + third
    println(5 divBy 6 == sum)

    val difference: Rational = half - third
    println(1 divBy 6 == difference)

    val product: Rational = half * third
    println(1 divBy 6 == product)

    val quotient: Rational = half / third
    println(3 divBy 2 == quotient)

    val negation: Rational = -half
    println(-1 divBy 2 == negation)

    println((2 divBy 1).toString() == "2")
    println((-2 divBy 4).toString() == "-1/2")
    println("117/1098".toRational().toString() == "13/122")

    val twoThirds = 2 divBy 3
    println(half < twoThirds)

    println(half in third..twoThirds)

    println(2000000000L divBy 4000000000L == 1 divBy 2)

    println("912016490186296920119201192141970416029".toBigInteger() divBy
            "1824032980372593840238402384283940832058".toBigInteger() == 1 divBy 2)
}

infix fun Number.divBy(num: Number): Rational = Rational(this.toLong().toBigInteger()) / Rational(num.toLong().toBigInteger())

data class Rational(val n:BigInteger, val d:BigInteger = BigInteger.ONE){

    private val num: BigInteger
    private val den: BigInteger
    init {

        if (d == BigInteger.ZERO) {
            throw IllegalArgumentException()
        }

        val (num, den) = normalize(n, d)
        this.num = num
        this.den = den
    }

    private fun normalize(nu: BigInteger, de:BigInteger): Pair<BigInteger, BigInteger>{
        val gcd = nu.gcd(de)
        val sign = if ((n < BigInteger.ZERO) == (d < BigInteger.ZERO)) 1 else -1
        val num = (nu.abs()/gcd.abs()) * sign.toBigInteger()
        val den = de.abs()/gcd.abs()
        println("num - $num & den - $den")
        return Pair(num, den)
    }

    operator fun plus(other: Rational): Rational{
        val d = this.den * other.den
        val n = d / this.den * this.num + d / other.den * other.num
        return Rational(n, d)
    }

    operator fun minus(other: Rational): Rational{
        val d = this.den * other.den
        val n = d / this.den * this.num - d / other.den * other.num
        return Rational(n, d)
    }
    operator fun times(other: Rational): Rational{
        val(n, d) = normalize(this.n * other.n, this.d * other.d)
        return Rational(n, d)
    }
    operator fun div(other: Rational): Rational{
        val (n, d) = normalize(this.n * other.d ,this.d * other.n)
        return Rational(n, d)
    }

    operator fun unaryMinus(): Rational {
        val (n, d) = Pair(-this.num, this.den)
        return Rational(n, d)
    }

    operator fun compareTo(rational: Rational): Int {
        if (this.den == rational.den){
            if (this.num < rational.num) return -1
            if(this.num == rational.num) return 0
            return 1
        }
        val a = this.num * rational.den
        val b = this.den * rational.num
        if (a < b) return -1
        return 1
    }

    operator fun rangeTo(rational: Rational): Pair<Rational, Rational> {
        return Pair(this, rational)
    }

    override fun toString(): String {
        if (d == BigInteger.ONE) return "$n"
        val (num,den) = normalize(n, d)
        if (den == BigInteger.ONE) return "$num"
        return "$num/$den"
    }

    override fun equals(other: Any?): Boolean {
        if (other is Rational)
            return (this.num == other.num && this.den == other.den)
        return false
    }

}

operator fun Pair<Rational, Rational>.contains(rational: Rational): Boolean {
    return rational >= this.first && rational < this.second
}

fun String.toRational() : Rational {
    val args = this.split("/");
    return if (args.size == 1) {
        Rational(args[0].toBigInteger())
    } else {
        Rational(args[0].toBigInteger(), args[1].toBigInteger())
    }
}