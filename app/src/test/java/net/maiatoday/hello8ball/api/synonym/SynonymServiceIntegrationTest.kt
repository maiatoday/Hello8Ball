package net.maiatoday.hello8ball.api.synonym

import com.google.common.truth.Truth
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

@Disabled("Integration tests hit the real api")
class SynonymServiceIntegrationTest {

    private lateinit var synonymService: SynonymService

    @BeforeEach
    fun setUp() {
        synonymService = SynonymService.instance
    }

    @AfterEach
    fun tearDown() {
    }

    @Test
    fun `success synonym service access`() = runBlocking {
        val response = synonymService.getSynonymAsync("test").await()
        val synonyms = response.synonyms
        Truth.assertThat(synonyms.size).isGreaterThan(0)
        Truth.assertThat(synonyms[0]).isNotEmpty()
    }
}