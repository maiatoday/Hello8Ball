package net.maiatoday.hello8ball

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.Mockito

class QuestionRepositoryTest {

    val mockQuestionInterface = Mockito.mock(QuestionInterface::class.java)

    @Test
    fun `should return 42 on life universe question`() = runBlocking {
        val subject = QuestionRepository(mockQuestionInterface)

        val answer = subject.ponder("the meaning of life the universe and everything")

        assertThat(answer).isEqualTo("42")
    }

    @Test
    fun `should detect prime number`() = runBlocking {
        val subject = QuestionRepository(mockQuestionInterface)

        val answer = subject.ponder("773")

        assertThat(answer).isEqualTo("773 is Prime")
    }

    @Test
    fun `should detect non prime number`() = runBlocking  {
        val subject = QuestionRepository(mockQuestionInterface)

        val answer = subject.ponder("774")

        assertThat(answer).isEqualTo("774 is not Prime")
    }

    @Test
    fun `should return answer from network`() = runBlocking  {
        Mockito.`when`(mockQuestionInterface.getAnswer()).thenReturn("Yes")
        val subject = QuestionRepository(mockQuestionInterface)

        val answer = subject.ponder("Any question")

        assertThat(answer).isEqualTo("Yes")
    }

    @Test
    fun `should return answer from fake network`() = runBlocking  {
        val subject = QuestionRepository(QuestionNetworkFake)

        val answer = subject.ponder("Any question")

        assertThat(answer).isIn(QuestionNetworkFake.answers)
    }

}