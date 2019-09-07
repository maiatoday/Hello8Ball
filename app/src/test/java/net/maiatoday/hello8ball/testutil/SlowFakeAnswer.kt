package net.maiatoday.hello8ball.testutil

import kotlinx.coroutines.delay
import net.maiatoday.hello8ball.question.QuestionInterface

class SlowFakeAnswer(
    private val timeout: Long,
    private val answer: String = "whatever"
) :
    QuestionInterface {
    override suspend fun getAnswer(question: String): String {
        delay(timeout)
        return answer
    }
}
