package net.maiatoday.hello8ball.testutil

import kotlinx.coroutines.delay
import net.maiatoday.hello8ball.question.QuestionInterface

class SlowFakeQuestion(private val timeout: Long) : QuestionInterface {
    override suspend fun getAnswer(question: String): String {
        delay(timeout)
        return "whatever"
    }
}
