package net.maiatoday.hello8ball.question

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import net.maiatoday.hello8ball.api.synonym.SynonymService
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Rule
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class QuestionSynonymTest {
    @get:Rule
    val server = MockWebServer()
    private lateinit var service: SynonymService
    private lateinit var subject: QuestionSynonym

    @BeforeEach
    fun setUp() {
        service = SynonymService.synonymService(server.url("/"))
        subject = QuestionSynonym(service)
    }

    @Test
    fun `🐱 valid response 202`() = runBlocking {
        server.enqueue(MockResponse().setBody(SYNONYM_FIXTURE))
        val answer = subject.getAnswer("kitten")
        assertThat(answer).isAnyOf("baby cat", "mitzi")
    }

    @Test
    fun `🐱 valid but empty response 202`() = runBlocking {
        server.enqueue(MockResponse().setBody(SYNONYM_EMPTY_FIXTURE))
        val answer = subject.getAnswer("sitar")
        assertThat(answer).isEqualTo("no synonym for sitar")
    }

    @Test
    fun `👹 bad response 404`() = runBlocking {
        server.enqueue(MockResponse().setResponseCode(404))
        val answer = subject.getAnswer("kitten")
        assertThat(answer).isEqualTo("no synonym for kitten")
    }

    @Test
    fun `👺 bad response 500`() = runBlocking {
        server.enqueue(MockResponse().setResponseCode(500))
        val answer = subject.getAnswer("kitten")
        assertThat(answer).isEqualTo("no synonym for kitten")
    }
}

const val SYNONYM_FIXTURE = """
    {
    "word":"kitten",
    "synonyms":["baby cat", "mitzi"]
    }
"""

const val SYNONYM_EMPTY_FIXTURE = """
    {
    "word":"sitar",
    "synonyms":[]
    }
"""
