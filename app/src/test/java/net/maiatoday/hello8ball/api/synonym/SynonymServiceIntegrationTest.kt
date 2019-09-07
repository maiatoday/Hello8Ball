package net.maiatoday.hello8ball.api.synonym

import com.google.common.truth.Truth
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Ignore
import org.junit.Test

@Ignore("Integration tests hit the real api")
class SynonymServiceIntegrationTest {

    private lateinit var synonymService: SynonymService

    @Before
    fun setUp() {
        synonymService = SynonymService.instance
    }

    @After
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