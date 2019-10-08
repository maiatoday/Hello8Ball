package net.maiatoday.hello8ball.util

import com.google.common.truth.Truth
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class PrimeTest {

    @ParameterizedTest(name = "{0} is prime")
    @ValueSource(ints = [2,3,7,773])
    fun `test for primeness`(value: Int) {
        Truth.assertWithMessage("Expect $value to be prime")
            .that(isPrime(value))
            .isTrue()
    }

    @ParameterizedTest(name = "{0} is not prime")
    @ValueSource(ints = [0,1,4,6,9,49,772])
    fun `test for not - primeness`(value: Int) {
        Truth.assertWithMessage("Expect $value to not be prime")
            .that(isPrime(value))
            .isFalse()
    }
}