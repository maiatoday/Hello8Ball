package net.maiatoday.hello8ball.question

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import net.maiatoday.hello8ball.question.QuestionEightBall
import net.maiatoday.hello8ball.question.QuestionRepository
import net.maiatoday.hello8ball.testutil.CoroutinesTestRule
import net.maiatoday.hello8ball.testutil.TestDispatcherProvider
import net.maiatoday.hello8ball.testutil.getValueForTest
import net.maiatoday.hello8ball.view.MyViewModel
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
@Ignore("Slow and fast tests for demo")
class SlowFastTests {

    // Set the main coroutines dispatcher for unit testing.
    // We are setting the above-defined testDispatcher as the Main thread dispatcher.
    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    val eightBall = QuestionEightBall
    val password = QuestionPassword()
    val synonym = QuestionSynonym()
    val testDispatcher = coroutinesTestRule.testDispatcher
    val contextProvider = TestDispatcherProvider(testDispatcher)

    @Test
    fun `🐢️ should return valid answer (delay)`() = runBlocking {
        val answer = QuestionEightBall.getAnswer()
        assertThat(answer).isIn(QuestionEightBall.answers)
    }

    @Test
    fun `🐰 should return valid answer (no delay)`() = runBlockingTest {
        val answer = QuestionEightBall.getAnswer()
        assertThat(answer).isIn(QuestionEightBall.answers)
    }

    @Test
    fun `🐢️ asking a real question returns an answer (delay)`() = runBlocking {
        val repository = QuestionRepository(
            eightBall = eightBall,
            password = password,
            synonym = synonym,
            contextProvider = contextProvider
        )
        val subject = MyViewModel(repository)

        subject.fetchAnswer("hello world")
        delay(3000)
        assertThat(subject.answer.getValueForTest()).isIn(eightBall.answers)
    }

    @Test
    fun `🐰 asking a real question returns an answer (no delay)`() =
        coroutinesTestRule.testDispatcher.runBlockingTest {
            pauseDispatcher {

                val repository =
                    QuestionRepository(
                        eightBall = eightBall,
                        password = password,
                        synonym = synonym,
                        contextProvider = contextProvider
                    )
                val subject = MyViewModel(repository)

                subject.fetchAnswer("hello world")
                advanceTimeBy(5000)
                assertThat(subject.answer.getValueForTest()).isIn(eightBall.answers)
            }
        }

    @Test
    fun `🐢️ should return answer from 🎱 (delay)`() = runBlocking {
        val subject = QuestionRepository(
            eightBall = eightBall,
            password = password,
            synonym = synonym,
            contextProvider = contextProvider
        )

        val answer = subject.ponder("Any question")

        assertThat(answer).isIn(eightBall.answers)
    }

    @Test
    fun `🐰 should return answer from 🎱 (no delay)`() =
        coroutinesTestRule.testDispatcher.runBlockingTest {
            val subject =
                QuestionRepository(
                    eightBall = eightBall,
                    password = password,
                    synonym = synonym,
                    contextProvider = contextProvider
                )

            val answer = subject.ponder("Any question")

            assertThat(answer).isIn(QuestionEightBall.answers)
        }

}