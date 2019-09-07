package net.maiatoday.hello8ball.util

import com.google.common.truth.Truth
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class PrimeTest(val number: Int, val prime: Boolean) {

    companion object {
        @JvmStatic
        @Parameterized.Parameters
        fun data() = listOf(
            arrayOf(0, false),
            arrayOf(1, false),
            arrayOf(2, true),
            arrayOf(3, true),
            arrayOf(4, false),
            arrayOf(6, false),
            arrayOf(7, true),
            arrayOf(9, false),
            arrayOf(49, false),
            arrayOf(773, true),
            arrayOf(772, false)
        )
    }

    @Test
    fun `test for primeness`() {
        Truth.assertWithMessage("Expect $number to be prime=${prime}")
            .that(isPrime(number))
            .isEqualTo(prime)
    }

}