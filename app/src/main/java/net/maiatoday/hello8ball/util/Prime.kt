package net.maiatoday.hello8ball.util

@Suppress("MagicNumber")
fun isPrime(n: Int): Boolean =
    when {
        (n <= 1) -> false
        (n <= 3) -> true
        ((n % 2 == 0) || (n % 3 == 0)) -> false
        else -> isPrimeLoop(n)
    }

@Suppress("MagicNumber")
fun isPrimeLoop(n: Int): Boolean {
    for (i in 5..n / 2 step 6) {
        if (n % i == 0 || n % (i + 2) == 0) {
            return false
        }
    }
    return true
}
