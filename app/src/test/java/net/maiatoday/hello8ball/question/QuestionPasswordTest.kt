package net.maiatoday.hello8ball.question

import com.google.common.truth.Truth
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import net.maiatoday.hello8ball.api.password.PasswordService
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class QuestionPasswordTest {
    @get:Rule
    val server = MockWebServer()
    private lateinit var service: PasswordService
    private lateinit var subject: QuestionPassword

    @Before
    fun setUp() {
        service = PasswordService.passwordService(server.url("/"))
        subject = QuestionPassword(service)
    }

    @Test
    fun `👹 bad response 404`() = runBlocking {
        server.enqueue(MockResponse().setResponseCode(404))
        val answer = subject.getAnswer("password")
        assertThat(answer).isEqualTo("Oops no password")
    }

    @Test
    fun `👺 bad response 500`() = runBlocking {
        server.enqueue(MockResponse().setResponseCode(500))
        val answer = subject.getAnswer("password")
        assertThat(answer).isEqualTo("Oops no password")
    }

    @Test
    fun `🐲 valid response 202`() = runBlocking {
            server.enqueue(MockResponse().setBody(PASSWORD_FIXTURE))
            val answer = subject.getAnswer("password")
            assertThat(answer).isEqualTo("dragon123")
        }
}

const val PASSWORD_FIXTURE = """
    {
    "char":["dragon123"]
    }
"""