package net.maiatoday.hello8ball.util

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class PrimeTest {

    @Test
    fun `check 773 is prime`() {
        assertThat(isPrime(773)).isTrue()
    }

    @Test
    fun `check 772 is not prime`() {
        assertThat(isPrime(772)).isFalse()
    }

}