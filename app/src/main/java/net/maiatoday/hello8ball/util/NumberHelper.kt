package net.maiatoday.hello8ball.util

fun isPrime(n: Int): Boolean {
    if (n <= 1) {
        return false
    } else if (n <= 3) {
        return true
    } else if ((n % 2 == 0) || (n % 3 == 0)) {
        return false
    }
    for (i in 5..n / 2 step 6) {
        if (n % i == 0 || n % (i + 2) == 0) {
            return false
        }
    }
    return true
}