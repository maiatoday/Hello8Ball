package net.maiatoday.hello8ball.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import net.maiatoday.hello8ball.question.QuestionEightBall
import net.maiatoday.hello8ball.question.QuestionInterface
import net.maiatoday.hello8ball.question.QuestionRepository
import net.maiatoday.hello8ball.testutil.CoroutinesTestRule
import net.maiatoday.hello8ball.testutil.TestDispatcherProvider
import net.maiatoday.hello8ball.testutil.getValueForTest
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

@ExperimentalCoroutinesApi
class MyViewModelTest {

    // Set the main coroutines dispatcher for unit testing.
    // We are setting the above-defined testDispatcher as the Main thread dispatcher.
    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    val testDispatcher = coroutinesTestRule.testDispatcher
    val contextProvider = TestDispatcherProvider(testDispatcher)

    @Test
    fun `loading is false in the beginning`() {
        val mockQuestionInterface = Mockito.mock(QuestionInterface::class.java)
        val repository = QuestionRepository(mockQuestionInterface)
        val subject = MyViewModel(repository)

        assertThat(subject.isloading.getValueForTest()).isFalse()
    }


    @Test
    fun `asking a question sets is loading`() {
        val mockQuestionInterface = Mockito.mock(QuestionInterface::class.java)
        val repository = QuestionRepository(mockQuestionInterface)
        val subject = MyViewModel(repository)

        subject.fetchAnswer("hello world")

        assertThat(subject.isloading.getValueForTest()).isTrue()
    }

    @Test
    fun `asking a question returns an answer`() =
        testDispatcher.runBlockingTest {
            val mockQuestionInterface = Mockito.mock(QuestionInterface::class.java)
            Mockito.`when`(mockQuestionInterface.getAnswer()).thenReturn("Yes")
            val repository = QuestionRepository(
                mockQuestionInterface,
                mockQuestionInterface,
                mockQuestionInterface,
                contextProvider
            )
            val subject = MyViewModel(repository)

            subject.fetchAnswer("hello world")

            assertThat(subject.answer.getValueForTest()).isEqualTo("Yes")
        }


    @Test
    fun `return an answer stops loading`() = testDispatcher.runBlockingTest {
        val mockQuestionInterface = Mockito.mock(QuestionInterface::class.java)
        Mockito.`when`(mockQuestionInterface.getAnswer()).thenReturn("Yes")
        val repository = QuestionRepository(
            mockQuestionInterface,
            mockQuestionInterface,
            mockQuestionInterface,
            contextProvider
        )
        val subject = MyViewModel(repository)

        subject.fetchAnswer("hello world")

        assertThat(subject.isloading.getValueForTest()).isFalse()
    }

    @Test
    fun `🚀 asking a real question returns an answer (no delay)`() =
        testDispatcher.runBlockingTest {
            pauseDispatcher {

                val mockQuestionInterface = Mockito.mock(QuestionInterface::class.java)
                val repository = QuestionRepository(
                    QuestionEightBall,
                    mockQuestionInterface,
                    mockQuestionInterface,
                    contextProvider
                )
                val subject = MyViewModel(repository)

                subject.fetchAnswer("hello world")
                advanceTimeBy(5000)
                assertThat(subject.answer.getValueForTest()).isIn(QuestionEightBall.answers)
            }
        }


}