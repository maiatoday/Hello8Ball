package net.maiatoday.hello8ball.question

import net.maiatoday.hello8ball.api.password.PasswordService
import net.maiatoday.hello8ball.question.QuestionInterface

class QuestionPassword(private val service: PasswordService = PasswordService.instance) :
    QuestionInterface {
    override suspend fun getAnswer(question: String): String {
        val response = service.getPasswordAsync().await()
        val passwords = response.char
        return passwords[0]
    }
}