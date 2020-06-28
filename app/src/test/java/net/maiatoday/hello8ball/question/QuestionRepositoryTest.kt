package net.maiatoday.hello8ball.question

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import net.maiatoday.hello8ball.testutil.TestDispatcherProvider
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.verify

@ExperimentalCoroutinesApi
class QuestionRepositoryTest {

    val mockQuestionInterface = Mockito.mock(QuestionInterface::class.java)
    val testDispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()
    val contextProvider = TestDispatcherProvider(testDispatcher)
    val eightBall = QuestionEightBall
    val password = QuestionPassword()
    val synonym = QuestionSynonym()

    @Test
    fun `should return 42 on life universe question`() = testDispatcher.runBlockingTest {
        val subject = QuestionRepository(
            eightBall = eightBall,
            password = password,
            synonym = synonym,
            contextProvider = contextProvider
        )
        val answer = subject.ponder("the meaning of life the universe and everything")

        assertThat(answer).isEqualTo("42")
    }

    @Test
    fun `should detect prime number`() = testDispatcher.runBlockingTest {
        val subject = QuestionRepository(
            eightBall = eightBall,
            password = password,
            synonym = synonym,
            contextProvider = contextProvider
        )
        val answer = subject.ponder("773")

        assertThat(answer).isEqualTo("773 is Prime")
    }

    @Test
    fun `should detect non prime number`() = testDispatcher.runBlockingTest {
        val subject = QuestionRepository(
            eightBall = eightBall,
            password = password,
            synonym = synonym,
            contextProvider = contextProvider
        )
        val answer = subject.ponder("774")

        assertThat(answer).isEqualTo("774 is not Prime")
    }

    @Test
    fun `should return answer from 🎱`() = testDispatcher.runBlockingTest {
        Mockito.`when`(mockQuestionInterface.getAnswer()).thenReturn("Yes")
        val subject = QuestionRepository(
            eightBall = mockQuestionInterface,
            password = password,
            synonym = synonym,
            contextProvider = contextProvider
        )
        val answer = subject.ponder("Any question")

        assertThat(answer).isEqualTo("Yes")
    }

    @Test
    fun `should return password 🐲`() = testDispatcher.runBlockingTest {
        Mockito.`when`(mockQuestionInterface.getAnswer()).thenReturn("dragon")
        val subject = QuestionRepository(
            eightBall = eightBall,
            password = mockQuestionInterface,
            synonym = synonym,
            contextProvider = contextProvider
        )
        val answer = subject.ponder("password")

        assertThat(answer).isEqualTo("dragon")
    }

    @Test
    fun `should return synonym 👯` () = testDispatcher.runBlockingTest {
        Mockito.`when`(mockQuestionInterface.getAnswer("word")).thenReturn("formulate")
        val subject = QuestionRepository(
            eightBall = eightBall,
            password = password,
            synonym = mockQuestionInterface,
            contextProvider = contextProvider
        )
        val answer = subject.ponder("word")

        assertThat(answer).isEqualTo("formulate")
        verify(mockQuestionInterface).getAnswer("word")
    }

    @Test
    fun `🚀 should return answer from 🎱 (no delay)`() = testDispatcher.runBlockingTest {
        val subject = QuestionRepository(
            eightBall = eightBall,
            password = password,
            synonym = synonym,
            contextProvider = contextProvider
        )
        val answer = subject.ponder("Any question")

        assertThat(answer).isIn(QuestionEightBall.answers)
    }

}