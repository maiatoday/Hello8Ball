package net.maiatoday.hello8ball.question

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import net.maiatoday.hello8ball.question.QuestionEightBall
import org.junit.Test

class QuestionEightBallTest {

    @ExperimentalCoroutinesApi
    @Test
    fun `🚀 should return valid answer (no delay)`() = runBlockingTest {
        val answer = QuestionEightBall.getAnswer()
        assertThat(answer).isIn(QuestionEightBall.answers)
    }

}