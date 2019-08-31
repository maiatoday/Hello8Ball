package net.maiatoday.hello8ball

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import net.maiatoday.hello8ball.testutil.CoroutinesTestRule
import net.maiatoday.hello8ball.testutil.getValueForTest
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

@Ignore
class FlakeyTests {
    // Set the main coroutines dispatcher for unit testing.
    // We are setting the above-defined testDispatcher as the Main thread dispatcher.
    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun `asking a question sets is loading`() = runBlocking {
        val mockQuestionInterface = Mockito.mock(QuestionInterface::class.java)
        val repository = QuestionRepository(mockQuestionInterface)
        val subject = MyViewModel(repository)

        subject.fetchAnswer("hello world")
        delay(1)

        Truth.assertThat(subject.isloading.getValueForTest()).isTrue()
    }

    @Test
    fun `return an answer stops loading`() = runBlocking {
        val mockQuestionInterface = Mockito.mock(QuestionInterface::class.java)
        Mockito.`when`(mockQuestionInterface.getAnswer()).thenReturn("Yes")
        val repository = QuestionRepository(mockQuestionInterface)
        val subject = MyViewModel(repository)

        subject.fetchAnswer("hello world")
        delay(1)

        Truth.assertThat(subject.isloading.getValueForTest()).isFalse()
    }
}