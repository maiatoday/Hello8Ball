package net.maiatoday.hello8ball.question

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import net.maiatoday.hello8ball.testutil.InstantExecutorExtension
import net.maiatoday.hello8ball.testutil.getValueForTest
import net.maiatoday.hello8ball.view.MyViewModel
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito

@ExperimentalCoroutinesApi
@ExtendWith(InstantExecutorExtension::class)
@Disabled("flakey tests for demo")
class FlakeyTests {

    @Test
    fun `asking a question sets is loading`() = runBlocking {
        val mockQuestionInterface = Mockito.mock(QuestionInterface::class.java)
        val repository = QuestionRepository(mockQuestionInterface)
        val subject = MyViewModel(repository)

        subject.fetchAnswer("hello world")
        delay(1000) // … the test might fail ¯\_(ツ)_/¯

        assertThat(subject.isloading.getValueForTest()).isTrue()
    }

    @Test
    fun `return an answer stops loading`() = runBlocking {
        val mockQuestionInterface = Mockito.mock(QuestionInterface::class.java)
        Mockito.`when`(mockQuestionInterface.getAnswer()).thenReturn("Yes")
        val repository = QuestionRepository(mockQuestionInterface)
        val subject = MyViewModel(repository)

        subject.fetchAnswer("hello world")
        //  delay(1000)

        assertThat(subject.isloading.getValueForTest()).isFalse()
    }

    @Test
    fun `asking a question sets is loading (flakey)`() {
        val mockQuestionInterface = Mockito.mock(QuestionInterface::class.java)
        val repository = QuestionRepository(mockQuestionInterface)
        val subject = MyViewModel(repository)

        subject.fetchAnswer("hello world")

        assertThat(subject.isloading.getValueForTest()).isTrue()

    }
}