package net.maiatoday.hello8ball.api.password

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

@Disabled("Integration tests hit the real api")
class PasswordServiceIntegrationTest {

    private lateinit var passwordService: PasswordService

    @BeforeEach
    fun setUp() {
        passwordService = PasswordService.instance
    }

    @AfterEach
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
