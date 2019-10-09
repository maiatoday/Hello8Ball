package net.maiatoday.hello8ball.question

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import net.maiatoday.hello8ball.testutil.InstantExecutorExtension
import net.maiatoday.hello8ball.testutil.TestDispatcherProvider
import net.maiatoday.hello8ball.testutil.getValueForTest
import net.maiatoday.hello8ball.view.MyViewModel
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExperimentalCoroutinesApi
@ExtendWith(InstantExecutorExtension::class)
@Disabled("Slow and fast tests for demo")
class SlowFastTests {

    val testDispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()
    val contextProvider = TestDispatcherProvider(testDispatcher)
    @BeforeEach
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }
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
        val repository = QuestionRepository(QuestionEightBall)
        val subject = MyViewModel(repository)

        subject.fetchAnswer("hello world")
        delay(3000)
        assertThat(subject.answer.getValueForTest()).isIn(QuestionEightBall.answers)
    }

    @Test
    fun `🐰 asking a real question returns an answer (no delay)`() =
        testDispatcher.runBlockingTest {
            pauseDispatcher {

                val repository =
                    QuestionRepository(contextProvider = contextProvider)
                val subject = MyViewModel(repository)

                subject.fetchAnswer("hello world")
                advanceTimeBy(5000)
                assertThat(subject.answer.getValueForTest()).isIn(QuestionEightBall.answers)
            }
        }

    @Test
    fun `🐢️ should return answer from 🎱 (delay)`() = runBlocking {
        val subject = QuestionRepository(QuestionEightBall)

        val answer = subject.ponder("Any question")

        assertThat(answer).isIn(QuestionEightBall.answers)
    }

    @Test
    fun `🐰 should return answer from 🎱 (no delay)`() =
        testDispatcher.runBlockingTest {
            val subject =
                QuestionRepository(contextProvider = contextProvider)

            val answer = subject.ponder("Any question")

            assertThat(answer).isIn(QuestionEightBall.answers)
        }

}