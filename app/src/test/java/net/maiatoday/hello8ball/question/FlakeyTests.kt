package net.maiatoday.hello8ball.question

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import net.maiatoday.hello8ball.testutil.CoroutinesTestRule
import net.maiatoday.hello8ball.testutil.getValueForTest
import net.maiatoday.hello8ball.util.DispatcherProvider
import net.maiatoday.hello8ball.view.MyViewModel
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

@ExperimentalCoroutinesApi
@Ignore("flakey tests for demo")
class FlakeyTests {
    // Set the main coroutines dispatcher for unit testing.
    // We are setting the above-defined testDispatcher as the Main thread dispatcher.
    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    val eightBall = QuestionEightBall
    val password = QuestionPassword()
    val synonym = QuestionSynonym()
    val contextProvider = DispatcherProvider()

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun `asking a question sets is loading`() = runBlocking {
        val mockQuestionInterface = Mockito.mock(QuestionInterface::class.java)
        val repository = QuestionRepository(mockQuestionInterface, password, synonym, contextProvider)
        val subject = MyViewModel(repository)

        subject.fetchAnswer("hello world")
        delay(1000) // … the test might fail ¯\_(ツ)_/¯

        assertThat(subject.isloading.getValueForTest()).isTrue()
    }

    @Test
    fun `return an answer stops loading`() = runBlocking {
        val mockQuestionInterface = Mockito.mock(QuestionInterface::class.java)
        Mockito.`when`(mockQuestionInterface.getAnswer()).thenReturn("Yes")
        val repository = QuestionRepository(mockQuestionInterface, password, synonym, contextProvider)
        val subject = MyViewModel(repository)

        subject.fetchAnswer("hello world")
        //  delay(1000)

        assertThat(subject.isloading.getValueForTest()).isFalse()
    }

    @Test
    fun `asking a question sets is loading (flakey)`() {
        val mockQuestionInterface = Mockito.mock(QuestionInterface::class.java)
        val repository = QuestionRepository(mockQuestionInterface, password, synonym, contextProvider)
        val subject = MyViewModel(repository)

        subject.fetchAnswer("hello world")

        assertThat(subject.isloading.getValueForTest()).isTrue()

    }
}