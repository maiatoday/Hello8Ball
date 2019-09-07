package net.maiatoday.hello8ball.api.password

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Ignore
import org.junit.Test

@Ignore("Integration tests hit the real api")
class PasswordServiceIntegrationTest {

    private lateinit var passwordService: PasswordService

    @Before
    fun setUp() {
        passwordService = PasswordService.instance
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `success password service access`() = runBlocking {
        val response = passwordService.getPasswordAsync().await()
        val passwords = response.char
        assertThat(passwords.size).isEqualTo(1)
        assertThat(passwords[0]).isNotEmpty()
    }
}
