package net.maiatoday.hello8ball

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test


class QuestionNetworkFakeTest {

    @ExperimentalCoroutinesApi
    @Test
    fun `🚀 should return valid answer (no delay)`() = runBlockingTest  {
        val answer = QuestionEightBall.getAnswer()
        assertThat(answer).isIn(QuestionEightBall.answers)
    }

}