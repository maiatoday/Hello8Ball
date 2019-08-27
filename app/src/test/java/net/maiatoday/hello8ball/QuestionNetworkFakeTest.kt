package net.maiatoday.hello8ball

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test


class QuestionNetworkFakeTest {

    @Test
    fun `should return valid answer (delay)`() = runBlocking  {
        val answer = QuestionNetworkFake.getAnswer()
        assertThat(answer).isIn(QuestionNetworkFake.answers)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `should return valid answer (no delay)`() = runBlockingTest  {
        val answer = QuestionNetworkFake.getAnswer()
        assertThat(answer).isIn(QuestionNetworkFake.answers)
    }

}