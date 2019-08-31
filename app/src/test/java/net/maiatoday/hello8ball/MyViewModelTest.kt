package net.maiatoday.hello8ball

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import net.maiatoday.hello8ball.testutil.CoroutinesTestRule
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
        coroutinesTestRule.testDispatcher.runBlockingTest {
            val mockQuestionInterface = Mockito.mock(QuestionInterface::class.java)
            Mockito.`when`(mockQuestionInterface.getAnswer()).thenReturn("Yes")
            val repository = QuestionRepository(
                mockQuestionInterface,
                coroutinesTestRule.testDispatcher,
                coroutinesTestRule.testDispatcher
            )
            val subject = MyViewModel(repository)

            subject.fetchAnswer("hello world")

            assertThat(subject.answer.getValueForTest()).isEqualTo("Yes")
        }


    @Test
    fun `return an answer stops loading`() = coroutinesTestRule.testDispatcher.runBlockingTest {
        val mockQuestionInterface = Mockito.mock(QuestionInterface::class.java)
        Mockito.`when`(mockQuestionInterface.getAnswer()).thenReturn("Yes")
        val repository = QuestionRepository(
            mockQuestionInterface,
            coroutinesTestRule.testDispatcher,
            coroutinesTestRule.testDispatcher
        )
        val subject = MyViewModel(repository)

        subject.fetchAnswer("hello world")

        assertThat(subject.isloading.getValueForTest()).isFalse()
    }

    @Test
    fun `🚀 asking a real question returns an answer (no delay)`() =
        coroutinesTestRule.testDispatcher.runBlockingTest {
            pauseDispatcher {

                val repository = QuestionRepository(
                    QuestionNetworkFake,
                    coroutinesTestRule.testDispatcher,
                    coroutinesTestRule.testDispatcher
                )
                val subject = MyViewModel(repository)

                subject.fetchAnswer("hello world")
                advanceTimeBy(5000)
                assertThat(subject.answer.getValueForTest()).isIn(QuestionNetworkFake.answers)
            }
        }


}