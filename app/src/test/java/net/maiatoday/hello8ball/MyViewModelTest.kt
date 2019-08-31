package net.maiatoday.hello8ball

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

@ExperimentalCoroutinesApi
class MyViewModelTest {
    val mockQuestionInterface = Mockito.mock(QuestionInterface::class.java)

    // Set the main coroutines dispatcher for unit testing.
    // We are setting the above-defined testDispatcher as the Main thread dispatcher.
    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun `loading is false in the beginning`() = coroutinesTestRule.testDispatcher.runBlockingTest {
        val repository = QuestionRepository(mockQuestionInterface)
        val subject = MyViewModel(repository)

        assertThat(getValueForTest(subject.isloading)).isFalse()
    }


    @Test
    fun `asking a question sets is loading`() = coroutinesTestRule.testDispatcher.runBlockingTest {
        val repository = QuestionRepository(mockQuestionInterface)
        val subject = MyViewModel(repository)

        subject.fetchAnswer("hello world")

        assertThat(getValueForTest(subject.isloading)).isTrue()
    }

    @Test
    fun `asking a question returns an answer`() = coroutinesTestRule.testDispatcher.runBlockingTest {
        Mockito.`when`(mockQuestionInterface.getAnswer()).thenReturn("Yes")
        val repository = QuestionRepository(mockQuestionInterface)
        val subject = MyViewModel(repository)

        subject.fetchAnswer("hello world")

        assertThat(getValueForTest(subject.answer)).isEqualTo("Yes")
    }


    @Test
    fun `return an answer stops loading`() = coroutinesTestRule.testDispatcher.runBlockingTest {
        Mockito.`when`(mockQuestionInterface.getAnswer()).thenReturn("Yes")
        val repository = QuestionRepository(mockQuestionInterface)
        val subject = MyViewModel(repository)

        subject.fetchAnswer("hello world")

        subject.isloading.observeForTesting {
            assertThat(subject.isloading.value).isFalse()
        }
    }

    @Ignore
    @Test
    fun `asking a real questions returns an answer`() = coroutinesTestRule.testDispatcher.runBlockingTest {

        val repository = QuestionRepository(QuestionNetworkFake)
        val subject = MyViewModel(repository)

        subject.fetchAnswer("hello world")
        coroutinesTestRule.testDispatcher.advanceTimeBy(3_000)
       // assertThat(getValueForTest(subject.answer)).isEqualTo("Yes")
        subject.answer.observeForTesting {
            assertThat(subject.answer.value).isEqualTo("title")
        }
    }

}